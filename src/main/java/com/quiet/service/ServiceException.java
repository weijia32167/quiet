package com.quiet.service;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/3/3
 * Desc   :
 */
public class ServiceException extends RuntimeException {
    public ServiceException (String message) {
        super(message);
    }

    public ServiceException (Throwable cause) {
        super(cause);
    }
}
