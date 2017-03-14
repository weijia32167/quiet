package com.quiet.concurrent.count;

import com.quiet.concurrent.AtomicFloat;

import java.util.concurrent.TimeUnit;

/**
 * Copyright com.quiet
 * Author : jiawei
 * Date   : 2016/5/20
 * Desc   :
 */
public class DurationAtomicFloat extends AbsDurationValue<Float>{

    private AtomicFloat count;

    public DurationAtomicFloat (int time, TimeUnit timeUnit) {
        super(time,timeUnit);
        count  = new AtomicFloat(0.0f);
    }

    public DurationAtomicFloat (int time, TimeUnit timeUnit,long startTime) {
        super(time,timeUnit,startTime);
        count  = new AtomicFloat(0.0f);
    }

    public DurationAtomicFloat (int time, TimeUnit timeUnit,float value) {
        super(time,timeUnit);
        count  = new AtomicFloat(value);
    }

    public DurationAtomicFloat (int time, TimeUnit timeUnit,long startTime,float value) {
        super(time,timeUnit,startTime);
        count  = new AtomicFloat(value);
    }


    public final void setValue(float newValue){
        count.set(newValue);
    }

    @Override
    final Float _getValue() {
        return count.get();
    }

    @Override
    public final void reset() {
        setValue(0);
    }
}
