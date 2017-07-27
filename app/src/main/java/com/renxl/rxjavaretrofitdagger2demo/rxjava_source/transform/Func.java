package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.transform;

/**
 * Created by renxl
 * On 2017/7/26 8:24.
 */

public interface Func<T, R> {
    R call(T t);
}
