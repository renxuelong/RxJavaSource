package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava;

/**
 * Created by renxl
 * On 2017/7/10 22:19.
 * 观察者
 * <p>
 * T 表示事件
 */

public abstract class Subscrible<T> {
    public abstract void onNext(T t);
}
