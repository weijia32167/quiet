package com.quiet.chain.validate.core;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public abstract class AbsStringValidate implements IValidate {

    protected String arg;

    public AbsStringValidate(String arg) {
        this.arg = arg;
    }


}
