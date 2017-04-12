package com.renxl.rxjavaretrofitdagger2demo.dagger2;

/**
 * Created by renxl
 * On 2017/4/11 15:59.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 当 Module 中实例化对象有冲突时使用
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DaggerQualifier {
    String value() default "Default String log";
}
