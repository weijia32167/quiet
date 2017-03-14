package com.quiet.concurrent.cacheline.model;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/7
 * Desc   :
 */
public class UnPaddingLong {

    private volatile long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
