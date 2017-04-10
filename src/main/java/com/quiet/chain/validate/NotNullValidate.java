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

    public NotNullValidate(String arg, String name) {
        super(arg);
        ERROR = name + " " + Constant.ERROR_NULL;
    }

    public NotNullValidate(String arg, String name, String errorMessageIfNull) {
        super(arg);
        ERROR = errorMessageIfNull;
    }

    @Override
    public void validate() throws ValidateException {
        if (arg == null) {
            ValidateException.throwException(ERROR);
        }
    }
}
