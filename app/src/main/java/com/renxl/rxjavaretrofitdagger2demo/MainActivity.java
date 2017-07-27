package com.renxl.rxjavaretrofitdagger2demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.renxl.rxjavaretrofitdagger2demo.dagger2.Dagger2Activity;
import com.renxl.rxjavaretrofitdagger2demo.retrofit.RetrofitActivity;
import com.renxl.rxjavaretrofitdagger2demo.rxjava.RxJavaActivity;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.RxJavaSourceActivity;
import com.renxl.rxjavaretrofitdagger2demo.rxjavaretrofitdagger2.RRDActivity;
import com.renxl.rxjavaretrofitdagger2demo.rxretrofit.RxJavaRetrofitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_retrofit)
    Button btnRetrofit;
    @BindView(R.id.RxJava)
    Button RxJava;
    @BindView(R.id.btn_rx_java_retrofit)
    Button btnRxJavaRetrofit;
    @BindView(R.id.btn_dagger2)
    Button btnDagger2;
    @BindView(R.id.btn_rrd)
    Button btnDDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_retrofit, R.id.RxJava, R.id.btn_rx_java_retrofit, R.id.btn_dagger2, R.id.btn_rrd, R.id.btn_rxjava_source})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.RxJava:
                startActivity(new Intent(this, RxJavaActivity.class));
                break;
            case R.id.btn_rx_java_retrofit:
                startActivity(new Intent(this, RxJavaRetrofitActivity.class));
                break;
            case R.id.btn_dagger2:
                startActivity(new Intent(this, Dagger2Activity.class));
                break;
            case R.id.btn_rrd:
                startActivity(new Intent(this, RRDActivity.class));
                break;
            case R.id.btn_rxjava_source:
                startActivity(new Intent(this, RxJavaSourceActivity.class));
                break;
        }
    }
}
