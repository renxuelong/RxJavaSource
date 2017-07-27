package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.scheduler;

import android.os.Handler;
import android.os.Looper;

import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Observable;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.OnSubscrible;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Subscrible;

/**
 * Created by renxl
 * On 2017/7/26 22:00.
 */

public class OnSubscribleMain<T> implements OnSubscrible<T> {

    Handler mHandler = new Handler(Looper.getMainLooper());

    Observable<T> mTObservable;

    public OnSubscribleMain(Observable<T> TObservable) {
        mTObservable = TObservable;
    }

    @Override
    public void call(Subscrible<? super T> subscrible) {
        MainChange<T> mainChange = new MainChange<>(subscrible);
        mTObservable.subscribe(mainChange);
    }


    private class MainChange<T> extends Subscrible<T> {

        Subscrible<? super T> real;

        MainChange(Subscrible<? super T> real) {
            this.real = real;
        }

        @Override
        public void onNext(final T t) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    real.onNext(t);
                }
            });
        }
    }
}
