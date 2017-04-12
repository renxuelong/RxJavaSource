package com.renxl.rxjavaretrofitdagger2demo.dagger2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by renxl
 * On 2017/4/11 14:09.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
