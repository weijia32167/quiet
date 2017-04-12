package com.quiet.chain.validate;

import com.quiet.chain.validate.core.ValidateException;

import java.util.Collections;
import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/4/10
 * Desc   :
 */
public class StringRangeValidate extends NotNullValidate {

    private final String ERROR_MESSAGE;
    private final Set<String> allowStrings;

    public StringRangeValidate(String arg, String name, Set<String> allowStrings) {
        super(arg, name);
        this.allowStrings = Collections.unmodifiableSet(allowStrings);
        ERROR_MESSAGE = name + "[" + arg + "] is not in Collection" + allowStrings.toString();
    }


    @Override
    public void validate() throws ValidateException {
        super.validate();
        if (!allowStrings.contains(arg)) {
            ValidateException.throwException(ERROR_MESSAGE);
        }
    }
}
