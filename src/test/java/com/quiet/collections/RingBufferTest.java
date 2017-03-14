package com.quiet.collections;

import com.quiet.collections.queue.NumberRingBuffer;
import com.quiet.collections.queue.RingBuffer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/6
 * Desc   :
 */
public class RingBufferTest {

    public static final Logger log = LoggerFactory.getLogger(RingBufferTest.class);

    public static final NumberRingBuffer RING_BUFFER = new NumberRingBuffer(4);

    @Before
    public void before() {

    }

    @Test
    public void test() {
        Assert.assertTrue(RING_BUFFER.empty());
        for (int i = 0; i < 10; i++) {
            RING_BUFFER.in(i);
            log.info(RING_BUFFER.list().toString() + " : " + RING_BUFFER.average(10).doubleValue() + "[" + RING_BUFFER.average(1).intValue() + "]");
        }
        Assert.assertTrue(RING_BUFFER.full());
    }

}
