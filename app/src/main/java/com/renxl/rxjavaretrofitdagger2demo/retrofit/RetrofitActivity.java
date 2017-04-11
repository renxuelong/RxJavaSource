package com.renxl.rxjavaretrofitdagger2demo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.renxl.rxjavaretrofitdagger2demo.Constants;
import com.renxl.rxjavaretrofitdagger2demo.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.btn_request)
    Button btnRequest;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_gson)
    Button btnGson;

    private OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_request, R.id.btn_gson})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                doHttpRequest();
                break;
            case R.id.btn_gson:
                doHttpRequestGson();
                break;
        }
    }

    /**
     * Retrofit 添加 GSON 转换器之后就可以将 ResponseBody 转化成想要的对象，并且还提供了异步请求的 API
     */
    private void doHttpRequestGson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PRODUCTION)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        Call<List<Advertising>> listCall = service.retrofitRequestGson(1);
        listCall.enqueue(new Callback<List<Advertising>>() {
            @Override
            public void onResponse(Call<List<Advertising>> call, Response<List<Advertising>> response) {
                List<Advertising> advertisings = response.body();
                StringBuilder sb = new StringBuilder();
                for (Advertising advertising : advertisings) {
                    sb.append(advertising.getName()).append(":").append("\n");
                }
                Log.i("renxl", sb.toString());
                tvResult.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<List<Advertising>> call, Throwable t) {

            }
        });

    }

    /**
     * Retrofit 的请求是同步的，必须创建线程在子线程中进行网络请求,在未提供 GSON 转化器之前是不可以将响应体转换成自己想要的对象的
     */
    private void doHttpRequest() {
        // 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.PRODUCTION).client(okHttpClient).build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        // 由 Retrofit 创建 Call 实例
        final Call<ResponseBody> responseBodyCall = service.retrofitRequest(1);
        new Thread() {
            @Override
            public void run() {
                super.run();
                Response<ResponseBody> response = null;
                try {
                    // 由 Call 实例发起请求得到响应体
                    response = responseBodyCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final StringBuilder sb = new StringBuilder();

                sb.append("Code: ").append(response.code()).append("\n");
                try {
                    // 响应体中的 ResponseBody
                    sb.append("Body: ").append(response.body().string()).append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append("IsSuccessful: ").append(response.isSuccessful()).append("\n");

                sb.append("Headers").append("\n");
                Headers headers = response.headers();
                sb.append(headers.toString()).append("\n");

                sb.append("Message").append(response.message()).append("\n");
                sb.append("ErrorBody").append(response.errorBody()).append("\n");

                Log.i("renxl", sb.toString());

                tvResult.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(sb.toString());
                    }
                });
            }
        }.start();

    }


}
