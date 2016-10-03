package com.example.glenn.twist.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.glenn.twist.models.Twist;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glenn on 24/08/2016.
 */
public class AddTwistRequest extends Request<Twist> {

    private final Response.Listener<Twist> listener;
    private final Gson gson = new Gson();
    private Map<String, String> mParams;

    private int userID;
    private String twistTitle;
    private String twistee;
    private String myAnswer;
    private String twisteeAnswer;
    private String reward;

    public AddTwistRequest(int requestMethod, String url, Response.Listener<Twist> listener, Response.ErrorListener errorListener,
                           int userID, String twistTitle, String twistee, String myAnswer, String twisteeAnswer, String reward) {
        super(requestMethod,url,errorListener);
        this.listener = listener;
        mParams = new HashMap<String, String>();
        mParams.put("userID", "" + userID);
        mParams.put("twistTitle", twistTitle);
        mParams.put("twistee", twistee);
        mParams.put("myAnswer", myAnswer);
        mParams.put("twisteeAnswer", twisteeAnswer);
        mParams.put("reward", reward);
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected Response<Twist> parseNetworkResponse(NetworkResponse response) {
        try {

            JSONObject twist = new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            Log.i("info", twist.toString());


            Twist relevantTwist = gson.fromJson(twist.toString(),Twist.class);
            return Response.success(relevantTwist, HttpHeaderParser.parseCacheHeaders(response));
        }catch(UnsupportedEncodingException uee){
            return  Response.error(new ParseError(uee));
        }catch(JSONException jsone){
            return  Response.error(new ParseError(jsone));
        }
    }

    @Override
    protected void deliverResponse(Twist response) {
        listener.onResponse(response);
    }
}
