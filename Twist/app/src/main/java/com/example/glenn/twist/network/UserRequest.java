package com.example.glenn.twist.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.glenn.twist.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glenn on 24/08/2016.
 */
public class UserRequest extends Request<List<User>> {
    private final Response.Listener<List<User>> listener;
    private Map<String, String> mParams;
    private final Gson gson = new Gson();

    public UserRequest(String user, String pass, int requestMethod, String url, Response.Listener<List<User>> listener, Response.ErrorListener errorListener) {
        super(requestMethod,url,errorListener);
        this.listener = listener;
        mParams = new HashMap<String, String>();
        mParams.put("name", user);
        mParams.put("pass", pass);
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }


    @Override
    protected Response<List<User>> parseNetworkResponse(NetworkResponse response) {
        try {
            JSONObject user = new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            JSONArray users = new JSONArray();

            JSONObject userModel = new JSONObject();
            userModel.put("ID", user.getInt("ID"));
            userModel.put("name", user.getString("name"));
            userModel.put("pass", user.getString("pass"));

            users.put(userModel);

            User[] relevantPosts = gson.fromJson(users.toString(),User[].class);
            return Response.success(Arrays.asList(relevantPosts), HttpHeaderParser.parseCacheHeaders(response));
        }catch(UnsupportedEncodingException uee){
            return  Response.error(new ParseError(uee));
        }catch(JSONException jsone){
            return  Response.error(new ParseError(jsone));
        }
    }

    @Override
    protected void deliverResponse(List<User> response) {
        listener.onResponse(response);
    }
}
