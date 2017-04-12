package com.renxl.rxjavaretrofitdagger2demo.dagger2;

import android.util.Log;

/**
 * Created by renxl
 * On 2017/4/10 21:05.
 */

public class User {

    private String log;

    // 使用 @Inject 注解声明这个类的对象可以被 Dagger2 初始化
//    @Inject
    public User() {
    }

    public User(String log) {
        this.log = log;
    }

    public void doSomething(String log) {
        Log.i("renxl", this.log + log);
    }

}
