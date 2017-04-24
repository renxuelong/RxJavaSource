package com.renxl.rxjavaretrofitdagger2demo.rxjavaretrofitdagger2;

import com.renxl.rxjavaretrofitdagger2demo.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renxl
 * On 2017/4/11 10:19.
 */

public class RRDClient {
    private static RRDService rrdService;

    public static RRDService getRRDService() {
        if (rrdService != null) return rrdService;
        return initRRDService();
    }

    private static RRDService initRRDService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PRODUCTION)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RRDService.class);
    }
}
