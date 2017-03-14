package com.quiet.concurrent.Lock;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/7
 * Desc   :
 */
public class LockTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(LockTest.class);

    public static final long REPEAT = 1000000000;

    private StopWatch stopWatch = new StopWatch();

    private TimeUnit TIMEUNIT = TimeUnit.SECONDS;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    public void testNoLock_NoContention() {
        stopWatch.start();
        for (Long i = 0l; i < REPEAT; ) {
            i++;
        }
        stopWatch.stop();
        LOGGER.info("noLock with no contention:[" + stopWatch.getTime(TIMEUNIT) + "]");
    }

    @Test
    public void testSynchronized_NoContention() {
        stopWatch.start();
        for (Long i = 0l; i < REPEAT; ) {
            synchronized (i) {
                i++;
            }
        }
        stopWatch.stop();
        LOGGER.info("synchronized with no contention:[" + stopWatch.getTime(TIMEUNIT) + "]");
    }

    @Test
    public void testLock_NoContention() {
        Lock lock = new ReentrantLock();
        stopWatch.start();
        for (Long i = 0l; i < REPEAT; ) {
            lock.lock();
            i++;
            lock.unlock();
        }
        stopWatch.stop();
        LOGGER.info("lock with no contention:[" + stopWatch.getTime(TIMEUNIT) + "]");
    }

    @Test
    public void testSynchronized_contention() {
        testContention("Synchronized");
    }

    @Test
    public void testLock_contention() {
        testContention("Lock");
    }


    private void testContention(String type) {
        final Long i = 0l;
        Thread thread = Thread.currentThread();
        executorService.submit(new LongAddTask(i, type, stopWatch, thread));
        executorService.submit(new LongAddTask(i, type, stopWatch, thread));
        LockSupport.park(thread);
        LOGGER.info(type + " with contention:[" + stopWatch.getTime(TIMEUNIT) + "]");
    }

    private static class LongAddTask implements Runnable {

        private Long value;

        private StopWatch stopWatch;

        private Thread thread;

        private String type;

        public LongAddTask(Long value, String type, StopWatch stopWatch, Thread thread) {
            this.value = value;
            this.type = type;
            this.stopWatch = stopWatch;
            this.thread = thread;
        }

        @Override
        public void run() {
            if (!stopWatch.isStarted()) {
                stopWatch.start();
            }
            switch (type) {
                case "Synchronized":
                    while (value < REPEAT) {
                        synchronized (value) {
                            value++;
                        }
                    }
                    break;
                case "Lock":
                    Lock lock = new ReentrantLock();
                    while (value < REPEAT) {
                        lock.lock();
                        value++;
                        lock.unlock();
                    }
                    break;
            }

            if (!stopWatch.isStopped()) {
                stopWatch.stop();
                LockSupport.unpark(thread);
            }
        }
    }
}

