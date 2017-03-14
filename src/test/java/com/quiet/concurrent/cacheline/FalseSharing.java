package com.quiet.concurrent.cacheline;

import com.quiet.concurrent.cacheline.model.PaddingLong;
import com.quiet.concurrent.cacheline.model.UnPaddingLong;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * CPU L1 L2 L3 CacheLine Test
 */
public class FalseSharing {

    public static final Logger LOGGER = LoggerFactory.getLogger(FalseSharing.class);

    public final static int NUM_THREADS = 4; // change
    //public final static long ITERATIONS = 500L * 1000L * 1000L;
    public final static long ITERATIONS = 50000000L;
    private static final StopWatch stopWatch = new StopWatch();

    private ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    private static PaddingLong paddingLong = new PaddingLong();
    private static UnPaddingLong unPaddingLong = new UnPaddingLong();

    @Test
    public void testPaddingLong() {
        test("Padding");
    }

    @Test
    public void testUnPaddingLong() {
        test("UnPadding");
    }

    private void test(String type) {
        Thread thread = Thread.currentThread();
        executorService.submit(new LongAddTask(type, thread));
        executorService.submit(new LongAddTask(type, thread));
        executorService.submit(new LongAddTask(type, thread));
        executorService.submit(new LongAddTask(type, thread));
        LockSupport.park(thread);
        LOGGER.info(type + "[" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "]");
    }


    public static final class LongAddTask implements Runnable {

        private String type;

        private Thread thread;

        public LongAddTask(String type, Thread thread) {
            this.type = type;
            this.thread = thread;
        }

        @Override
        public void run() {
            if (!stopWatch.isStarted()) {
                stopWatch.start();
            }
            long i = ITERATIONS + 1;
            while (0 != --i) {
                switch (type) {
                    case "Padding":
                        paddingLong.setValue(paddingLong.getValue() - 1);
                        break;
                    case "UnPadding":
                        unPaddingLong.setValue(unPaddingLong.getValue() - 1);
                        break;
                }
            }
            if (!stopWatch.isStopped()) {
                stopWatch.stop();
                LockSupport.unpark(thread);
            }
        }
    }
}