package com.renxl.rxjavaretrofitdagger2demo.RxJavaRetrofitDagger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.renxl.rxjavaretrofitdagger2demo.R;
import com.renxl.rxjavaretrofitdagger2demo.retrofit.Advertising;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by renxl
 * On 2017/4/11 10:07.
 */

public class RRDActivity extends AppCompatActivity {
    @BindView(R.id.btn_rrd)
    Button btnRrd;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Inject
    RRDService rrdService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rrd);
        ButterKnife.bind(this);

        DaggerRRDComponent.builder().build().inject(this);

    }

    @OnClick(R.id.btn_rrd)
    public void onViewClicked() {


        rrdService.getList(1).subscribeOn(Schedulers.io())
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
