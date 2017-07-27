package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava;

/**
 * Created by renxl
 * On 2017/7/10 22:17.
 * <p>
 * 被观察者
 * <p>
 * T 要处理的事件
 * <p>
 * Subscrible 观察者
 * <p>
 * Subscrible<? super T>   表示可以处理某种事件的观察者， ? super T 表示泛指的事件，所以用 super
 * <p>
 * super 泛型 用于方法参数类型限定，不能用于返回参数的限定，如果用于返回参数直接返回 Object 或者具体子类都没区别了，所以只能用于参数限定
 * <p>
 * extends 泛型 用于返回参数类型的限定，不能用于参数类型的限定
 */

public interface OnSubscrible<T> extends Action1<Subscrible<? super T>> {

}
