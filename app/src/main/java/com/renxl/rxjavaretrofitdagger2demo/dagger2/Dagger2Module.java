package com.renxl.rxjavaretrofitdagger2demo.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by renxl
 * On 2017/4/10 21:07.
 */

// 如果使用 Module 来实例化对象，则需要定义以下类
@Module // 声明自己是 Module
public class Dagger2Module {

    Context mContext;

    public Dagger2Module(Context context) {
        mContext = context;
    }

    @Provides
        // 必须添加 @Provides 声明这个方法是帮助 Dagger2 实例化对象的方法
    User providerUser() { // 这就是实例化对象的方法
        return new User(); // 完成实例化对象的逻辑
    }
}
