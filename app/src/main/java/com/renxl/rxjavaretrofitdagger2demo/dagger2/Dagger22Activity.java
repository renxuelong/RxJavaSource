package com.renxl.rxjavaretrofitdagger2demo.dagger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.renxl.rxjavaretrofitdagger2demo.R;
import com.renxl.rxjavaretrofitdagger2demo.RRDApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

/**
 * Created by renxl
 * On 2017/4/11 13:55.
 */

public class Dagger22Activity extends AppCompatActivity {
    @BindView(R.id.btn_dagger22)
    Button btnDagger22;
    @BindView(R.id.tv_dagger22)
    TextView tvDagger22;

    @DaggerQualifier("B")
    @Inject
    User user;

    @Inject
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger22);
        ButterKnife.bind(this);

        ((RRDApp) getApplication()).getSubDagger2Component().inject(this);
    }

    @OnClick(R.id.btn_dagger22)
    public void onViewClicked() {
        user.doSomething("Dagger2 生效 USER" + user.toString() + "\n" + okHttpClient.toString());
    }
}
