package com.example.glenn.twist.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.glenn.twist.models.Twist;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Glenn on 24/08/2016.
 */
public class TwistRequest extends Request<List<Twist>> {
    private final Response.Listener<List<Twist>> listener;
    private final Gson gson = new Gson();

    public TwistRequest(int requestMethod, String url, Response.Listener<List<Twist>> listener, Response.ErrorListener errorListener) {
        super(requestMethod,url,errorListener);
        this.listener = listener;
    }

    protected Response<List<Twist>> parseNetworkResponse(NetworkResponse response) {
        try {
            JSONArray posts = new JSONArray();
            JSONArray subreddit = new JSONArray(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));

            for(int i = 0; i < subreddit.length(); i++) {
                JSONObject data = subreddit.getJSONObject(i);
                JSONObject postmodel = new JSONObject();
                postmodel.put("title", data.getString("twisttitle"));
                postmodel.put("twistee", data.get("twistee"));
                postmodel.put("myAnswer", data.get("myanswer"));
                postmodel.put("twisteeAnswer", data.get("twisteeanswer"));
                postmodel.put("reward", data.get("reward"));
                postmodel.put("won", data.get("won"));
                posts.put(postmodel);
            }

            Twist[] relevantPosts = gson.fromJson(posts.toString(),Twist[].class);
            return Response.success(Arrays.asList(relevantPosts), HttpHeaderParser.parseCacheHeaders(response));
        }catch(UnsupportedEncodingException uee){
            return  Response.error(new ParseError(uee));
        }catch(JSONException jsone){
            return  Response.error(new ParseError(jsone));
        }
    }

    @Override
    protected void deliverResponse(List<Twist> response) {
        listener.onResponse(response);
    }

}
