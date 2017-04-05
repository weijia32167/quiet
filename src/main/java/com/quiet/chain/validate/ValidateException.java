package com.quiet.chain.validate;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/15
 * Desc   :
 */
public class ValidateException extends Exception {
    public ValidateException() {
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static final void throwException(String errorMessage) throws ValidateException {
        throw new ValidateException(errorMessage);
    }

}
