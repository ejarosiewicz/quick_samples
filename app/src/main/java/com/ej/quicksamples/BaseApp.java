package com.ej.quicksamples;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ej.quicksamples.utils.Constants;

/**
 * Instance of the Base Application.
 *
 * @author Emil Jarosiewicz
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(Constants.SHOULD_ANIMATE,true).commit();
        super.onCreate();
    }


    @Override
    public void onTerminate() {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(Constants.SHOULD_ANIMATE,true).commit();
        super.onTerminate();
    }


}
