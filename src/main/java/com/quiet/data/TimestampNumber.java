package com.quiet.data;

import java.sql.Timestamp;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/13
 * Desc   :
 */
public class TimestampNumber extends Number implements INumber {
    private Number value;
    private Timestamp timestamp;

    public TimestampNumber(Timestamp timestamp, Number value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public Number getNumber() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return value + "[" + timestamp + "]";
    }
}
