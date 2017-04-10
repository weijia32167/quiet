package com.quiet.chain.validate.core;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public abstract class AbsStringValidate implements IValidate {

    protected String arg;

    protected String name;

    public AbsStringValidate(String arg, String name) {
        this.arg = arg;
        this.name = name;
    }


}
