package com.codingtu.cooltu.processor.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ModuleInfo {
    //模块名
    String module();

    Class baseAct();

    Class baseFragment();

    //默认的R的包名
    String rPkg();

}
