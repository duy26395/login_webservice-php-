package com.example.duy26.login_webservice.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static  String TAG = SessionManager.class.getSimpleName();

    SharedPreferences preferences;

    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    private static  final  String PREF_NAME = "Android_Login";
    private static  final String KEY_IS_LOGGED_IN = "islooger";

    public  SessionManager (Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public  void setLogin(Boolean isLogin)
    {
        editor.putBoolean(KEY_IS_LOGGED_IN,isLogin);

        editor.commit();
    }
    public boolean isLoginIN()
    {
        return  preferences.getBoolean(KEY_IS_LOGGED_IN,false);
    }
}
