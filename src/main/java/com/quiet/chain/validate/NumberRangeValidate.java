package com.quiet.chain.validate;

import com.quiet.chain.validate.core.AbsNumberValidate;
import com.quiet.chain.validate.core.ValidateException;
import com.quiet.math.Arith;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/22
 * Desc   :
 */
public class NumberRangeValidate extends AbsNumberValidate {

    private Number min;
    private Number max;
    private final String ERROR;

    public NumberRangeValidate(String name, Number value, Number min, Number max) {
        super(name, value);
        this.min = min;
        this.max = max;
        ERROR = name + "[" + value.toString() + "]" + Constant.ERROR_NUMBER_RANGE + "[" + min + "," + max + "]";
    }

    public NumberRangeValidate(String name, Number value, Number min, Number max, String errorMessageIfNotRange) {
        super(name, value);
        this.min = min;
        this.max = max;
        ERROR = errorMessageIfNotRange;
    }


    @Override
    public void validate() throws ValidateException {
        if (value == null) {
            ValidateException.throwException(name + " " + Constant.ERROR_NULL);
        }

        if (min == null) {
            ValidateException.throwException("min " + Constant.ERROR_NULL);
        }

        if (max == null) {
            ValidateException.throwException("max " + Constant.ERROR_NULL);
        }

        if (Arith.compare(value, min) < 0 || Arith.compare(value, max) > 0) {
            throw new ValidateException(ERROR);
        }
    }

}
