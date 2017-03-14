package com.quiet.util;

import com.quiet.concurrent.cacheline.Sequence;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/8
 * Desc   :
 */
public class UnsafeUtilTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(UnsafeUtilTest.class);

    @Test
    public void test() throws Exception {
        Object obj1 = new Object();
        Object obj2 = new Object();
        ObjectA objectA1 = new ObjectA();
        ObjectA objectA2 = new ObjectA();

        Sequence sequence1 = new Sequence();
        Sequence sequence2 = new Sequence();
        Object[] sequences = new Object[6];
        sequences[0] = sequence1;
        sequences[1] = sequence2;
        sequences[2] = obj1;
        sequences[3] = obj2;
      /*  System.out.println("JVM地址大小:"+UnSafeUtil.addressOf(sequences[0] ));
        System.out.println("JVM地址大小:"+UnSafeUtil.addressOf(sequences[1]));
        System.out.println("JVM地址大小:"+UnSafeUtil.addressOf(sequences[2] ));
        System.out.println("JVM地址大小:"+UnSafeUtil.addressOf(sequences[3]));
        System.out.println(UnSafeUtil.getUnsafe().objectFieldOffset(Sequence.class.getField("value")));*/
      /*  System.out.println(UnSafeUtil.getAddressSize());
        System.out.println(UnSafeUtil.getObjectRefSize());*/
       /* System.out.println(UnSafeUtil.getObjectHeaderSize());*/
       /* System.out.println(UnSafeUtil.sizeOf(sequences,false));*/
        /*System.out.println(UnSafeUtil.getStartAddress(sequence1));
        System.out.println(UnSafeUtil.getStartAddress(sequence2));*/
        long address = UnSafeUtil.address(sequences);
        System.out.println(Long.toHexString(address));
        long a0 = UnSafeUtil.getUnsafe().getLong(address);
        long a1 = UnSafeUtil.getUnsafe().getLong(address + 8);
        System.out.println(Long.toHexString(a0));
        System.out.println(Long.toHexString(a1));
        /* System.out.println("对象大小:"+UnSafeUtil.sizeOf(paddingLong1));*/
       /*  PaddingLong paddingLong1 = new PaddingLong();
        System.out.println("JVM地址大小:"+UnSafeUtil.getUnsafe().addressSize() );   //地址大小会决定对象头的大小
        System.out.println("JVM引用大小:"+UnSafeUtil.getObjectRefSize() );          //引用大小
        System.out.println("对象大小:"+UnSafeUtil.sizeOf(new LhsPadding()));
        System.out.println("对象大小:"+UnSafeUtil.sizeOf(new Value()));
        System.out.println("对象大小:"+UnSafeUtil.sizeOf(new RhsPadding()));
        System.out.println("对象大小:"+UnSafeUtil.sizeOf(new Sequence()));
        System.out.println("对象大小:"+UnSafeUtil.sizeOf(paddingLong));
        System.out.println("对象地址:"+UnSafeUtil.addressOf(sequences));
        System.out.println("对象地址:"+UnSafeUtil.addressOf(paddingLong));
        System.out.println("对象地址:"+UnSafeUtil.addressOf(paddingLong1));*/
    /*   final ClassIntrospector ci = new ClassIntrospector();
        ObjectInfo res;
        res = ci.introspect(new ObjectA());
        System.out.println(res.toString());*/
    }

    @Test
    public void testArray() throws Exception {
        Sequence sequence1 = new Sequence();
        Sequence sequence2 = new Sequence();
        Sequence sequence3 = new Sequence();
        Sequence sequence4 = new Sequence();
        Object[] sequences = new Object[7];
        sequences[0] = sequence1;
        sequences[1] = sequence2;
        sequences[2] = sequence3;
        long sequencesAddress = UnSafeUtil.address(sequences);
        long klass = UnSafeUtil.getUnsafe().getLong(sequencesAddress + 8);
     /*   LOGGER.info("start address: "+Long.toHexString(sequencesAddress)+"");
        LOGGER.info("klass value: "+Long.toHexString(klass)+"");*/
        int i = 1;
        LOGGER.info("array size: " + UnSafeUtil.shallowSizeof(sequences));
    }


    @Test
    public void printObject() {
        Object[] sequences = new Object[1];
        sequences[0] = new Sequence(1);
        ArrayQueue queue = new ArrayQueue(8);

        long address = UnSafeUtil.address(Thread.currentThread());
        byte[][] result = UnSafeUtil.getBytes(Thread.currentThread());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getProperty("line.separator"));
        for (int i = 0; i < result.length; i++) {
            stringBuilder.append(Long.toHexString((address + i * 8))).append("   ");
            for (int j = 0; j < result[result.length - 1].length; j++) {
                stringBuilder.append(NumberUtil.byte2HexString(result[i][j])).append(" ");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        LOGGER.info("address size: " + UnSafeUtil.getAddressSize());
        LOGGER.info("header size: " + UnSafeUtil.getObjectHeaderSize());
        LOGGER.info("size: " + UnSafeUtil.shallowSizeof(Thread.currentThread()));
        LOGGER.info(stringBuilder.toString());
    }


    private static final int BUFFER_PAD;   //32
    private static long REF_ARRAY_BASE; //144
    private static final int REF_ELEMENT_SHIFT;  //2
    private static final Unsafe UNSAFE = UnSafeUtil.getUnsafe();

    static {
        final int scale = UNSAFE.arrayIndexScale(Object[].class);
        if (4 == scale) {
            REF_ELEMENT_SHIFT = 2;
        } else if (8 == scale) {
            REF_ELEMENT_SHIFT = 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
        BUFFER_PAD = 128 / scale;
        // Including the buffer pad in the array base offset
        REF_ARRAY_BASE = UNSAFE.arrayBaseOffset(Object[].class);
        REF_ARRAY_BASE = REF_ARRAY_BASE + (BUFFER_PAD << REF_ELEMENT_SHIFT);
    }


    private static class ObjectA {
        byte a = 1;
        char b = 'a';
        boolean c = false;
        short d = 2;

        int e = 10;
        long f = 1;
        float g = 12.3f;
        double h = 20.32321d;

        String str = "abc";
    }

    private static class ObjectB {
        long o = 10;
        int a = 5;
        Object m = new Object();
    }

}
