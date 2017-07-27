package com.renxl.rxjavaretrofitdagger2demo.rxjava_source.transform;

import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.OnSubscrible;
import com.renxl.rxjavaretrofitdagger2demo.rxjava_source.rxjava.Subscrible;

/**
 * Created by renxl
 * On 2017/7/26 8:21.
 */

public class OnSubscribleT<T, R> implements OnSubscrible<R> {

    OnSubscrible<T> mOnSubscrible;
    Func mTransform;

    public OnSubscribleT(OnSubscrible<T> onSubscrible, Func<? super T, ? extends R> transform) {
        mOnSubscrible = onSubscrible;
        mTransform = transform;
    }

    @Override
    public void call(Subscrible<? super R> subscrible) {
        Subscrible<? super T> proxy = new OperaChange(subscrible, mTransform);
        mOnSubscrible.call(proxy);
    }

    private class OperaChange<T, R> extends Subscrible<T> {

        Subscrible<? super R> actual;
        private Func<? super T, ? extends R> transform;

        OperaChange(Subscrible<? super R> actual, Func<? super T, ? extends R> transform) {
            this.actual = actual;
            this.transform = transform;
        }

        @Override
        public void onNext(T t) {
            R r = transform.call(t);
            actual.onNext(r);
        }
    }
}
