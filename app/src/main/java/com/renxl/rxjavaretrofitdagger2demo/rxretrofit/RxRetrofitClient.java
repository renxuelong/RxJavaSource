package com.renxl.rxjavaretrofitdagger2demo.rxretrofit;

import com.renxl.rxjavaretrofitdagger2demo.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renxl
 * On 2017/4/10 17:32.
 */

class RxRetrofitClient {

    private static Retrofit mRetrofit;

    private static Retrofit getRxRetrofit() {
        if (mRetrofit != null) return mRetrofit;
        return instanceRetrofit();
    }

    private static Retrofit instanceRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.PRODUCTION)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加 Retrofit 与 RxJava 转化的 Adapter
                .build();
        return mRetrofit;
    }

    static RxRetrofitService getRxRetrofitService() {
        return getRxRetrofit().create(RxRetrofitService.class);
    }
}
