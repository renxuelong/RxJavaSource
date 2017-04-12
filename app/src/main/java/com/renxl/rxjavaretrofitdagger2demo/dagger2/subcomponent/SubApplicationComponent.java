package com.renxl.rxjavaretrofitdagger2demo.dagger2.subcomponent;

import com.renxl.rxjavaretrofitdagger2demo.RRDApp;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.AppModule;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.Dagger2Module;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by renxl
 * On 2017/4/11 15:14.
 */
@Singleton
@Component(modules = AppModule.class)
public interface SubApplicationComponent {
    SubDagger2Component plus(Dagger2Module module);

    void inject(RRDApp app);
}
