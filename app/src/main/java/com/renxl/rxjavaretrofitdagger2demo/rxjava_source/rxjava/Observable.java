package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava;

import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.scheduler.OnSubscribleIO;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.scheduler.OnSubscribleMain;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.transform.Func;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.transform.OnSubscribleT;

/**
 * Created by renxl
 * On 2017/7/10 22:16.
 * T 代表要处理的参数类型
 */

public class Observable<T> {

    private OnSubscrible<T> onSubscrible;

    public Observable(OnSubscrible<T> onSubscrible) {
        this.onSubscrible = onSubscrible;
    }

    // 通过静态的 create 传入被观察者对象并构造场景 Observable 对象
    public static <T> Observable<T> create(OnSubscrible<T> onSubscrible) {
        return new Observable<>(onSubscrible);
    }

    // 通过 subscribe 方法传入观察者 Subscrible 对象，观察者一旦传入，就会调用被观察者的 call 方法
    // 这里 call 方法的参数为观察者，这样在被观察者的 call 方法终究可以调用观察者的方法，将事件传递
    public void subscribe(Subscrible<? super T> subscrible) {
        onSubscrible.call(subscrible);
    }


    public <R> Observable<R> map(Func<? super T, ? extends R> transform) {
        return new Observable<>(new OnSubscribleT<>(onSubscrible, transform));
    }

    public Observable<T> subscribleOn() {
        return new Observable(new OnSubscribleIO<>(this));
    }

    public Observable<T> observeOn() {
        return new Observable<>(new OnSubscribleMain<>(this));
    }
}
