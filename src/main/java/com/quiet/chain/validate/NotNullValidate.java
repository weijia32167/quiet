package com.quiet.chain.validate;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/15
 * Desc   :
 */
public class NotNullValidate extends AbsStringValidate {

    public NotNullValidate(String name) {
        super(name);
    }

    @Override
    public void validate() throws ValidateException {
        if (name == null) {
            ValidateException.throwException(name + " " + Constant.ERROR_NULL);
        }
    }
}
