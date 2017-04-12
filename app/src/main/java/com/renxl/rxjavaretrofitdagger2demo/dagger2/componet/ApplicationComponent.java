package com.renxl.rxjavaretrofitdagger2demo.dagger2.componet;

import com.renxl.rxjavaretrofitdagger2demo.dagger2.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by renxl
 * On 2017/4/11 14:35.
 */
@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {
    OkHttpClient getOkHttpClient();
}
