package com.renxl.rxjavaretrofitdagger2demo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.Dagger2Module;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.subcomponent.DaggerSubApplicationComponent;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.subcomponent.SubApplicationComponent;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.subcomponent.SubDagger2Component;

/**
 * Created by renxl
 * On 2017/4/10 15:45.
 */

public class RRDApp extends Application {
    SubApplicationComponent subApplicationComponent;
    SubDagger2Component subDagger2Component;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        subApplicationComponent = DaggerSubApplicationComponent.builder().build();
        subApplicationComponent.inject(this);
    }

    public SubDagger2Component getSubDagger2Component() {
        if (subDagger2Component == null)
            subDagger2Component = subApplicationComponent.plus(new Dagger2Module());

        return subDagger2Component;
    }
}
