package com.quiet.chain.validate;

import com.quiet.chain.validate.core.ValidateException;
import com.quiet.math.Arith;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public class StringNumberRangeValidate extends StringToNumberValidate {

    private Number min;
    private Number max;
    private final String ERROR;

    public StringNumberRangeValidate(String value, Class clazz, Number min, Number max) {
        super(value, clazz);
        this.min = min;
        this.max = max;
        ERROR = value + " " + Constant.ERROR_NUMBER_RANGE + "[" + min + "," + max + "]";
    }

    public StringNumberRangeValidate(String name, Class clazz, Number min, Number max, String errorMessageIfNotRange) {
        super(name, clazz);
        this.min = min;
        this.max = max;
        this.ERROR = errorMessageIfNotRange;
    }

    @Override
    public void validate() throws ValidateException {
        super.validate();
        Number value = getValue();
        if (Arith.compare(value, min) < 0 || Arith.compare(value, max) > 0) {
            throw new ValidateException(ERROR);
        }
    }

    public Number getValue() {
        String className = clazz.getName();
        Number value = null;
        switch (className) {
            case "java.lang.Byte":
                value = Byte.parseByte(name);
                break;
            case "java.lang.Integer":
                value = Integer.parseInt(name);
                break;
            case "java.lang.Short":
                Short.parseShort(name);
                break;
            case "java.lang.Long":
                value = Long.parseLong(name);
                break;
            case "java.lang.Float":
                value = Float.parseFloat(name);
                break;
            case "java.lang.Double":
                value = Double.parseDouble(name);
                break;
        }
        return value;
    }
}
