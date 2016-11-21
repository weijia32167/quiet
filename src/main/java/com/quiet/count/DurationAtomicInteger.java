package com.quiet.count;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright com.quiet
 * Author : jiawei
 * Date   : 2016/5/17
 * Desc   : 统计一段时间的数据量
 */
public class DurationAtomicInteger extends AbsDurationValue<Integer>{

    private AtomicInteger count;

    public DurationAtomicInteger (int time, TimeUnit timeUnit) {
        super(time,timeUnit);
        count  = new AtomicInteger(0);
    }
    public DurationAtomicInteger (int time, TimeUnit timeUnit,long startTime) {
        super(time,timeUnit,startTime);
        count  = new AtomicInteger(0);
    }

    public DurationAtomicInteger (int time, TimeUnit timeUnit,int initValue) {
        super(time,timeUnit);
        count  = new AtomicInteger(initValue);
    }

    public DurationAtomicInteger (int time, TimeUnit timeUnit,long startTime,int initValue) {
        super(time,timeUnit,startTime);
        count  = new AtomicInteger(initValue);
    }


    public final void setValue(int newValue){
        count.set(newValue);
    }

    public final void reset() {
        count.set(0);
    }

    @Override
    final Integer _getValue() {
        return count.get();
    }
}
