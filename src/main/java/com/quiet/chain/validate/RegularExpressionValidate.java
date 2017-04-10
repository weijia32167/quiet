package com.quiet.chain.validate;

import com.quiet.chain.validate.core.ValidateException;

import java.util.regex.Pattern;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/5
 * Desc   :
 */
public abstract class RegularExpressionValidate extends NotNullValidate {

    private final String regularExpression;

    private final Pattern pattern;

    private final String ERROR;

    public RegularExpressionValidate(String arg, String name, String regularExpression) {
        super(arg, name);
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        ERROR = name + " " + Constant.ERROR_REGULAR_EXPRESSION + " " + regularExpression;
    }

    public RegularExpressionValidate(String arg, String name, String regularExpression, String errorMessage) {
        super(arg, name);
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        ERROR = errorMessage;
    }

    @Override
    public void validate() throws ValidateException {
        super.validate();
        if (!pattern.matcher(arg).matches()) {
            ValidateException.throwException(ERROR);
        }
    }
}
