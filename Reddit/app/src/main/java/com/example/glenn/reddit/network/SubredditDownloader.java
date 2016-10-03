package com.example.glenn.reddit.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.glenn.reddit.models.Post;

import java.util.List;

/**
 * Created by Glenn on 23/08/2016.
 */
public class SubredditDownloader {
    private RequestQueue requestQueue;
    private static SubredditDownloader instance;
    private Context context;
    private final String BASEURL = "https://api.reddit.com";

    //Geeft instance terug => Singleton
    public static SubredditDownloader getInstance(Context context){
        if(instance == null){
            instance = new SubredditDownloader(context);
        }
        return instance;
    }

    private SubredditDownloader(Context context){
        //private constructor => Singleton
        this.context = context;
        requestQueue = Volley.newRequestQueue(this.context);
    }

    public void getPostsFromSubReddit(final VolleyCallBack<List<Post>> callback, String subreddit){
        Log.i("Posts origin","API");
        SubredditRequest request = new SubredditRequest(Request.Method.GET,BASEURL+"/r/"+subreddit+".json", new Response.Listener<List<Post>>(){
            @Override
            public void onResponse(List<Post> response) {
                callback.onSuccess(response);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("info", "ERROR");
                callback.onError(error);
            }
        });

        requestQueue.add(request);
    }

    public interface VolleyCallBack<T>{
        void onSuccess(T result);
        void onError(VolleyError error);
    }
}
