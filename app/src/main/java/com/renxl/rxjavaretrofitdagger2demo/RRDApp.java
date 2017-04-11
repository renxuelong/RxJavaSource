package com.renxl.rxjavaretrofitdagger2demo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by renxl
 * On 2017/4/10 15:45.
 */

public class RRDApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
