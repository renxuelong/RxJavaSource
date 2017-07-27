package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.scheduler;

import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Observable;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.OnSubscrible;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Subscrible;

/**
 * Created by renxl
 * On 2017/7/26 21:42.
 */

public class OnSubscribleIO<T> implements OnSubscrible<T> {

    Observable<T> mTObservable;

    public OnSubscribleIO(Observable<T> TObservable) {
        mTObservable = TObservable;
    }

    @Override
    public void call(final Subscrible<? super T> subscrible) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTObservable.subscribe(subscrible);
            }
        }).start();
    }
}
