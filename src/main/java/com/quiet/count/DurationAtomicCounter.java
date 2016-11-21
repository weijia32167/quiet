package com.quiet.count;

import java.util.concurrent.TimeUnit;

/**
 * Copyright com.quiet
 * Author : jiawei
 * Date   : 2016/5/20
 * Desc   : 基于时间段的原子计数器
 */
public class DurationAtomicCounter extends DurationAtomicInteger {

    public DurationAtomicCounter (int time, TimeUnit timeUnit) {
        super(time, timeUnit);
    }

    public DurationAtomicCounter (int time, TimeUnit timeUnit,long startTime) {
        super(time, timeUnit ,startTime);
    }

    public void count(){
        setValue(getValue()+1);
    }

}
