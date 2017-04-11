package com.renxl.rxjavaretrofitdagger2demo.RxJavaRetrofitDagger2;

import dagger.Component;

/**
 * Created by renxl
 * On 2017/4/11 10:31.
 */
@Component(modules = RRDModule.class)
public interface RRDComponent {

    void inject(RRDActivity activity);
}
