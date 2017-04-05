package com.quiet.chain.validate.core;



/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public abstract class AbsNumberValidate implements IValidate {

    protected Number value;

    public AbsNumberValidate(Number value) {
        this.value = value;
    }

}
