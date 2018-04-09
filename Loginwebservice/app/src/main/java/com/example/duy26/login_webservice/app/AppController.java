package com.example.duy26.login_webservice.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue requestQueue;
    private  static  AppController mController;

    @Override
    public void onCreate() {
        super.onCreate();
        mController = this;
    }
    public static  synchronized  AppController getmController(){
        return mController;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequetsQueue(Request<T> req , String tag)
    {
        req.setTag((TextUtils.isEmpty(tag)) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequetsQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void canclePendingRequest(Object tag){
        if(requestQueue !=null)
        {
            requestQueue.cancelAll(tag);
        }
    }
}
