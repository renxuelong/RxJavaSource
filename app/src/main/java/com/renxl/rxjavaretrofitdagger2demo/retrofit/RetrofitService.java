package com.renxl.rxjavaretrofitdagger2demo.retrofit;

import com.renxl.rxjavaretrofitdagger2demo.Constants;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by renxl
 * On 2017/4/10 14:28.
 */

interface RetrofitService {
    @GET(Constants.HOME_BANNER)
    Call<ResponseBody> retrofitRequest(@Query("type") int type);

    @GET(Constants.HOME_BANNER)
    Call<List<Advertising>> retrofitRequestGson(@Query("type") int type);
}
