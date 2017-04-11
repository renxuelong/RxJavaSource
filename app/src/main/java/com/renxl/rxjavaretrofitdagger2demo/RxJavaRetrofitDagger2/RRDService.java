package com.renxl.rxjavaretrofitdagger2demo.RxJavaRetrofitDagger2;

import com.renxl.rxjavaretrofitdagger2demo.Constants;
import com.renxl.rxjavaretrofitdagger2demo.retrofit.Advertising;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by renxl
 * On 2017/4/11 10:16.
 */

public interface RRDService {
    @GET(Constants.HOME_BANNER)
    Observable<List<Advertising>> getList(@Query("type") int type);
}
