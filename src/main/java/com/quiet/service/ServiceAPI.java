package com.quiet.service;

import java.lang.annotation.*;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/3/8
 * Desc   :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAPI {
    Class api();
    Class impl();
}
