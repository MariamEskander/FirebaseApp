package com.mariamnarouz.firebaseapp.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mariamnarouz.firebaseapp.CheckForMessages;
import com.mariamnarouz.firebaseapp.ui.login.LoginActivity;
import com.mariamnarouz.firebaseapp.data.model.User;

/**
 * Created by Mariam.Narouz on 12/26/2017.
 */

public class Singleton {
    private static Singleton mInstance = null;

    SharedPreferences myPrefs;
    SharedPreferences.Editor prefsEditor;
    private static final String ACTION_CLOSE_SERVICEE = "action.Close_SERVICE";
    Context context;
    private User user;
    private String token ;

    private Singleton(Context context){
        this.context = context;
        myPrefs = context.getSharedPreferences("MOIACommittee", 0);
        prefsEditor = myPrefs.edit();
    }

    public static Singleton getInstance(Context context){
        if(mInstance == null)
            mInstance = new Singleton(context);
        return mInstance;
    }

    public User getUser() {
        if(user == null) {
            String user_st = myPrefs.getString("user" , null);
            if (user_st != null) {
                user = new Gson().fromJson(user_st, User.class);
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        prefsEditor.putString("user", new Gson().toJson(user));
        prefsEditor.apply();
    }

    public String getToken() {
        if(token == null) {
            String string = myPrefs.getString("token" , null);
            if (string != null) {
                token = string;
            }
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        prefsEditor.putString("token", token);
        prefsEditor.apply();
    }

    public void logout() {
        user = null;
        prefsEditor.clear();
        prefsEditor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
        Intent intent = new Intent(context, CheckForMessages.class);
        context.stopService(intent);
    }


}