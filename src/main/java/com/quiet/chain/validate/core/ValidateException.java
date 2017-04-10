package com.quiet.chain.validate.core;

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

    public static final void throwException(Throwable e) throws ValidateException {
        throw new ValidateException(e);
    }

    public static final void throwException(String errorMessage, Throwable e) throws ValidateException {
        throw new ValidateException(errorMessage, e);
    }

}
