package com.renxl.rxjavaretrofitdagger2demo.rxretrofit;

import com.renxl.rxjavaretrofitdagger2demo.Constants;
import com.renxl.rxjavaretrofitdagger2demo.retrofit.Advertising;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by renxl
 * On 2017/4/10 17:30.
 */

public interface RxRetrofitService {

    @GET(Constants.HOME_BANNER)
    Observable<List<Advertising>> getAdvertisings(@Query("type") int type);

    @GET(Constants.HOME_BANNER)
    Observable<List<Advertising>> getAdvertising(@Query("type") int type);
}
