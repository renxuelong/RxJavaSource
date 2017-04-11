package com.renxl.rxjavaretrofitdagger2demo.rxretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.renxl.rxjavaretrofitdagger2demo.R;
import com.renxl.rxjavaretrofitdagger2demo.retrofit.Advertising;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by renxl
 * On 2017/4/10 17:25.
 */

public class RxJavaRetrofitActivity extends AppCompatActivity {

    @BindView(R.id.btn_request)
    Button btnRequest;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_request)
    public void onViewClicked() {
        doRxJavaRetrofitRequest();
    }

    private void doRxJavaRetrofitRequest() {
        RxRetrofitClient.getRxRetrofitService().getAdvertisings(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Advertising>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Advertising> value) {
                        Log.i("renxl", "result" + value.toString());
                        tvResult.setText(value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
