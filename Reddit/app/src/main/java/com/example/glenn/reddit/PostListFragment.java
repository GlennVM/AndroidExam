package com.example.glenn.reddit;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.glenn.reddit.models.Post;
import com.example.glenn.reddit.network.SubredditDownloader;
import com.example.glenn.reddit.persistence.Constants;
import com.example.glenn.reddit.persistence.RedditDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Glenn on 24/08/2016.
 */
public class PostListFragment extends Fragment {
    //Bind lokale variabele aan RecyclerView
    @Bind(R.id.postList)
    RecyclerView postlist;

    private boolean existsInDb;
    private Cursor subRedditContent;
    private String currentSubreddit;
    private SubredditDownloader downloader;
    private SubredditDownloader.VolleyCallBack<List<Post>> callback;
    private RedditDB db;

    public PostListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate de layout in het fragment
        View layout = inflater.inflate(R.layout.fragment_post_list, container, false);
        ButterKnife.bind(this, layout);

        //haalt een instantie op van de subredditdownloader
        downloader = SubredditDownloader.getInstance(getContext());
        //Maakt nieuwe layoutManager, die verplicht is voor RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //Voegt layoutManager toe aan de recyclerView
        postlist.setLayoutManager(layoutManager);

        //Dit wordt uitgevoerd als downloader.getposts wordt uitgevoerd
        callback = new SubredditDownloader.VolleyCallBack<List<Post>>() {
            @Override
            public void onSuccess(List<Post> result) {
                //Maakt nieuwe PostAdapter aan en we geven de downloads mee
                PostAdapter adapter = new PostAdapter(result);
                //We stellen de adapter in op de recyclerView
                postlist.setAdapter(adapter);

                if (existsInDb) {
                    //Dit zorgt ervoor dat we dubbele data vermijden
                    db.deleteSubreddit(Constants.PostEntry.TABLE_NAME, "subreddit=?", new String[]{currentSubreddit});
                    //We houden enkel de data over die nog niet in de databank zitten
                }

                //We voegen alles toe in de db dat er nog niet in zat
                for (int i = 0; i < result.size(); i++) {
                    Post post = result.get(i);
                    String title = post.getTitle();
                    int upvotes = post.getUpvotes();
                    Log.i("info","" + upvotes);
                    ContentValues values = new ContentValues();
                    values.put(Constants.PostEntry.COLUMN_NAME_POST_TITLE, title);
                    values.put(Constants.PostEntry.COLUMN_NAME_POST_SUBREDDIT, currentSubreddit);
                    values.put(Constants.PostEntry.COLUMN_NAME_POST_UPVOTES, upvotes);

                    db.insertSubreddit(Constants.PostEntry.TABLE_NAME,
                            null,
                            values);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Volley error", error.getMessage());
            }
        };

        SharedPreferences prefs = getActivity().getSharedPreferences("reddit", Context.MODE_PRIVATE);
        String subRedditToLoad = prefs.getString("lastVisitedSubreddit", "");
        Log.i("lastclicked", subRedditToLoad);

        loadSubreddit(subRedditToLoad, false);

        return layout;
    }

    private void checkSubredditInDatabase(String subreddit) {
        //Maakt nieuwe instantie van de db
        db = new RedditDB(getActivity());
        db.open();

        String[] args = {subreddit};
        String[] columns = {
                Constants.PostEntry._ID,
                Constants.PostEntry.COLUMN_NAME_POST_TITLE,
                Constants.PostEntry.COLUMN_NAME_POST_UPVOTES,
                Constants.PostEntry.COLUMN_NAME_POST_SUBREDDIT
        };

        //Haalt de subreddits op uit de db
        subRedditContent = db.getSubreddits(Constants.PostEntry.TABLE_NAME,
                columns, "subreddit=?", args, null, null, null, null);
        //Als de lengte van de cursor <= 0 dan zit het in niet in de db
        if (subRedditContent.getCount() <= 0) {
            existsInDb = false;
            Log.i("DB", subreddit + " is not in database");
        } else {
            existsInDb = true;
            Log.i("DB", subreddit + " is in database");

        }

    }

    public void loadSubreddit(String subreddit, boolean refresh) {
        //stel adapter in op postlist
        postlist.setAdapter(null);
        currentSubreddit = subreddit;

        //Controlleert of subreddit in db zit
        checkSubredditInDatabase(currentSubreddit);
        //Als het aanwezig is halen we dit op uit de databank
        if (existsInDb && refresh == false) {
            loadFromDb();
        } else {
            //Anders downloaden we posts
            downloader.getPostsFromSubReddit(callback, currentSubreddit);
        }
    }

    private void loadFromDb() {
        Log.i("Posts origin","DATABASE");
        //stelt adapter in
        postlist.setAdapter(null);
        //Maakt nieuwe lijst om data in te steken
        List<Post> data = new ArrayList<>();
        //Verplaats naar het eerste veld van de cursor
        subRedditContent.moveToFirst();
        Log.i("cursor count", Integer.toString(subRedditContent.getCount()));
        //cursor is al gefiltered op subreddit
        while (subRedditContent.isAfterLast() == false) {
            //We halen gegevens op van de subReddit
            String title = subRedditContent.getString(subRedditContent.getColumnIndex("title"));
            int upvotes = subRedditContent.getInt(subRedditContent.getColumnIndex("upvotes"));
            //We maken per item in de cursor een nieuwe Post aan
            Post post = new Post(title, upvotes);
            //We voegen die toe aan de data
            data.add(post);
            //Verplaatsen cursor naar de volgende
            subRedditContent.moveToNext();
        }
        Log.i("list count", String.valueOf(data.size()));
        //We maken een nieuwe adapter aan en geven de opgehaalde data mee
        PostAdapter adapter = new PostAdapter(data);
        //we stellen deze adapter in in de recyclerView
        postlist.setAdapter(adapter);
        //We sluiten de cursor
        subRedditContent.close();
    }

    private class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

        private List<Post> posts;

        public PostAdapter(List<Post> posts) {
            this.posts = posts;
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //We maken een nieuwe viewHolder aan en selecteren de juiste layout
            return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            //We stellen per post de juiste gegevens in
            Post post = posts.get(position);
            holder.title.setText(post.getTitle());
            holder.upvotes.setText(Integer.toString(post.getUpvotes()));
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }

        public class PostViewHolder extends RecyclerView.ViewHolder {

            private final TextView title;
            private final TextView upvotes;

            public PostViewHolder(View itemView) {
                super(itemView);
                title = ButterKnife.findById(itemView, R.id.postTitle);
                upvotes = ButterKnife.findById(itemView, R.id.postUpvotes);
            }
        }
    }
}
