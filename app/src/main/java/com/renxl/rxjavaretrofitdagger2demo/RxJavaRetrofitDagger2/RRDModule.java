package com.renxl.rxjavaretrofitdagger2demo.RxJavaRetrofitDagger2;

import com.renxl.rxjavaretrofitdagger2demo.Constants;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renxl
 * On 2017/4/11 10:27.
 */
@Module
public class RRDModule {

    @Provides
    RRDService provideRRDService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PRODUCTION)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RRDService.class);
    }
}
