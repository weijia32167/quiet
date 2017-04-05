package com.quiet.chain.validate;

import com.quiet.chain.validate.core.AbsStringValidate;
import com.quiet.chain.validate.core.ValidateException;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/15
 * Desc   :
 */
public class NotNullValidate extends AbsStringValidate {

    private final String ERROR;

    public NotNullValidate(String name) {
        super(name);
        ERROR = name + " " + Constant.ERROR_NULL;
    }

    public NotNullValidate(String name, String errorMessageIfNull) {
        super(name);
        ERROR = errorMessageIfNull;
    }

    @Override
    public void validate() throws ValidateException {
        if (name == null) {
            ValidateException.throwException(ERROR);
        }
    }
}
