package com.renxl.rxjavaretrofitdagger2demo.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.renxl.rxjavaretrofitdagger2demo.Constants;
import com.renxl.rxjavaretrofitdagger2demo.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RxJavaActivity extends AppCompatActivity {

    @BindView(R.id.btn_rxjava_request)
    Button btnRxjavaRequest;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        disposable = new CompositeDisposable();
    }

    @OnClick(R.id.btn_rxjava_request)
    public void onViewClicked() {
        doRxJavaRequest();
    }

    private void doRxJavaRequest() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
                Request request = new Request.Builder().url(Constants.PRODUCTION + Constants.HOME_BANNER + "?type=1").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException io) {
                        e.onError(io);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        e.onNext(response);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Response value) {
                        try {
                            String result = value.body().string();
                            Log.i("renxl", "onNext" + result);
                            tvResult.setText(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("renxl", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("renxl", "onComplete");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
