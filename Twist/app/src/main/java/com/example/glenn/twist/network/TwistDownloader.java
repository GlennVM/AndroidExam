package com.example.glenn.twist.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.glenn.twist.models.Twist;
import com.example.glenn.twist.models.User;

import java.util.List;

/**
 * Created by Glenn on 24/08/2016.
 */
public class TwistDownloader {
    private RequestQueue requestQueue;
    private static TwistDownloader instance;
    private Context context;
    private final String BASEURL = "http://1f1c60ca.ngrok.io/";

    public static TwistDownloader getInstance(Context context) {
        if (instance == null) {
            instance = new TwistDownloader(context);
        }
        return instance;
    }

    private TwistDownloader(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(this.context);
    }

    public void getUser(final VolleyCallBack<List<User>> callBack, String endpoint, String username, String pass) {
        UserRequest request = new UserRequest(username, pass, Request.Method.POST,BASEURL+endpoint,new Response.Listener<List<User>>(){

            @Override
            public void onResponse(List<User> response) {
                callBack.onSuccess(response);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error);
            }
        });

        requestQueue.add(request);
    }

    public void getTwists(final VolleyCallBack<List<Twist>> callBack, String endpoint) {
        TwistRequest request = new TwistRequest(Request.Method.GET,BASEURL+endpoint,new Response.Listener<List<Twist>>(){
            @Override
            public void onResponse(List<Twist> response) {
                for (int i = 0; i < response.size(); i++) {
                    Log.i("info", response.get(i).toString());
                }
                callBack.onSuccess(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error);
            }
        });

        requestQueue.add(request);
    }

    public void addTwist(final VolleyCallBack<Twist> callBack, String endpoint,
                         int userID, String twistTitle, String twistee, String myAnswer, String twisteeAnswer, String reward) {
        Log.i("info", "In AddTwist");
        AddTwistRequest request= new AddTwistRequest(Request.Method.POST,BASEURL+endpoint,new Response.Listener<Twist>(){
            @Override
            public void onResponse(Twist response) {
                callBack.onSuccess(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error);
            }
        }, userID, twistTitle, twistee, myAnswer, twisteeAnswer, reward);
        requestQueue.add(request);
    }

    public void twistWon(final VolleyCallBack<Twist> callBack, String endpoint, int userID, String twistTitle, Boolean won){
        WonTwistRequest request = new WonTwistRequest(Request.Method.POST, BASEURL+endpoint,new Response.Listener<Twist>(){
            @Override
            public void onResponse(Twist response) {
                callBack.onSuccess(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error);
            }
        }, userID, twistTitle, won);
        requestQueue.add(request);
    }

    public interface VolleyCallBack<T>{
        void onSuccess(T result);
        void onError(VolleyError error);
    }
}
