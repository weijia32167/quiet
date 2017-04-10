package com.quiet.chain.validate.core;



/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public abstract class AbsNumberValidate implements IValidate {

    protected String name;

    protected Number value;

    public AbsNumberValidate(String name, Number value) {
        this.name = name;
        this.value = value;
    }

}
