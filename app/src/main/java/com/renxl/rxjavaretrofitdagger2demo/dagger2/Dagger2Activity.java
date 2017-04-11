package com.renxl.rxjavaretrofitdagger2demo.dagger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.renxl.rxjavaretrofitdagger2demo.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renxl
 * On 2017/4/10 20:50.
 */

public class Dagger2Activity extends AppCompatActivity {
    @BindView(R.id.btn_dagger)
    Button btnDagger;

    // 声明需要注入的对象
    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        ButterKnife.bind(this);

        // 绑定注入的 Component，如果没这个步骤则不会成功注入
        DaggerDagger2Component
                .builder()
                .dagger2Module(new Dagger2Module(this)) // 如果 Component 中使用了 Module 则必须添加 Module
                .build()
                .inject(this);

    }

    @OnClick(R.id.btn_dagger)
    public void onViewClicked() {
        user.doSomething("Dagger2 生效 USER");
    }
}
