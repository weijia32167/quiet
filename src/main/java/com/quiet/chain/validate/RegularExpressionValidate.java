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

    public RegularExpressionValidate(String name, String regularExpression) {
        super(name);
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        ERROR = name + " " + Constant.ERROR_REGULAR_EXPRESSION + " " + regularExpression;
    }

    public RegularExpressionValidate(String name, String regularExpression, String errorMessage) {
        super(name);
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        ERROR = errorMessage;
    }

    @Override
    public void validate() throws ValidateException {
        super.validate();
        if (!pattern.matcher(name).matches()) {
            ValidateException.throwException(ERROR);
        }
    }
}
