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

    public NumberRangeValidate(Number value, Number min, Number max) {
        super(value);
        this.min = min;
        this.max = max;
        ERROR = value + " " + Constant.ERROR_NUMBER_RANGE + "[" + min + "," + max + "]";
    }

    public NumberRangeValidate(Number value, Number min, Number max, String errorMessageIfNotRange) {
        super(value);
        this.min = min;
        this.max = max;
        ERROR = errorMessageIfNotRange;
    }


    @Override
    public void validate() throws ValidateException {
        if (Arith.compare(value, min) < 0 || Arith.compare(value, max) > 0) {
            throw new ValidateException(ERROR);
        }
    }

}
