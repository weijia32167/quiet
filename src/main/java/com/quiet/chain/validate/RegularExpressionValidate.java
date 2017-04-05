package com.quiet.chain.validate;

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

    private final String ERROR_MESSAGE;

    public RegularExpressionValidate(String name, String regularExpression) {
        super(name);
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        ERROR_MESSAGE = name + " " + Constant.ERROR_REGULAR_EXPRESSION + " " + regularExpression;
    }

    public RegularExpressionValidate(String name, String regularExpression, String errorMessage) {
        super(name);
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        ERROR_MESSAGE = errorMessage;
    }

    @Override
    public void validate() throws ValidateException {
        super.validate();
        if (!pattern.matcher(name).matches()) {
            ValidateException.throwException(ERROR_MESSAGE);
        }
    }
}
