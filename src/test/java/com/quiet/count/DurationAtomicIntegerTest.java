package com.quiet.count;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/21
 * Desc   :
 */
public class DurationAtomicIntegerTest {

    @Test
    public void testInteger(){
        long initNano = System.nanoTime();
        final Thread mainThread = Thread.currentThread();
        final DurationAtomicCounter durationAtomicCounter = new DurationAtomicCounter(1, TimeUnit.SECONDS,initNano);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                durationAtomicCounter.count();
            }
        },0,1,TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(durationAtomicCounter.getValue()+"["+durationAtomicCounter.getCycle()+"]");
            }
        }, 0, 3, TimeUnit.SECONDS);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                LockSupport.unpark(mainThread);
            }
        },10,TimeUnit.SECONDS);
        LockSupport.park(mainThread);
    }





}
