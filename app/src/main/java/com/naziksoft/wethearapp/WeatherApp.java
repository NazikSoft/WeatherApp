package com.naziksoft.wethearapp;

import android.app.Application;

import com.naziksoft.wethearapp.db.DBHelperFactory;


public class WeatherApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // connect to DB
        DBHelperFactory.setDbHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        DBHelperFactory.releaseHelper();
        super.onTerminate();
    }
}
