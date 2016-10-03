package com.example.glenn.reddit.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.glenn.reddit.models.Post;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Glenn on 23/08/2016.
 */
public class SubredditRequest extends Request<List<Post>> {

    private final Response.Listener<List<Post>> listener;
    private final Gson gson = new Gson();

    public SubredditRequest(int requestMethod,String url, Response.Listener<List<Post>> listener,Response.ErrorListener errorListener){
        super(requestMethod,url,errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<List<Post>> parseNetworkResponse(NetworkResponse response) {
        try {
            JSONObject subreddit = new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            JSONObject data = subreddit.getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            JSONArray posts = new JSONArray();

            for(int i = 0 ; i < children.length(); i++){
                JSONObject postdata = children.getJSONObject(i).getJSONObject("data");
                Log.i("info", postdata.toString());
                JSONObject postmodel = new JSONObject();
                postmodel.put("title",postdata.getString("title"));
                // postmodel.put("author",postdata.getString("author"));
                postmodel.put("upvotes",postdata.getInt("ups"));

                posts.put(postmodel);
            }

            Post[] relevantPosts = gson.fromJson(posts.toString(),Post[].class);
            return Response.success(Arrays.asList(relevantPosts), HttpHeaderParser.parseCacheHeaders(response));
        }catch(UnsupportedEncodingException uee){
            return  Response.error(new ParseError(uee));
        }catch(JSONException jsone){
            return  Response.error(new ParseError(jsone));
        }
    }

    @Override
    protected void deliverResponse(List<Post> response) {
        listener.onResponse(response);
    }
}
