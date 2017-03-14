package com.quiet.data;

import java.sql.Timestamp;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/13
 * Desc   :
 */
public class TimeSlotNumber extends Number implements INumber {
    private Number value;
    private Timestamp start;
    private Timestamp end;

    public TimeSlotNumber(Timestamp start, Timestamp end, Number value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.start = start;
        this.end = end;
        this.value = value;
    }

    @Override
    public Number getNumber() {
        return value;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEnd() {
        return end;
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

    @Override
    public String toString() {
        return value + "[" + start + "--" + end + "]";
    }
}
