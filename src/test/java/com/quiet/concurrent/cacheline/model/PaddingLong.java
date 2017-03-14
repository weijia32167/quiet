package com.quiet.concurrent.cacheline.model;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/7
 * Desc   :  http://ifeve.com/cpu-cache-flushing-fallacy/
 * /sys/devices/system/cpu/cpu0/cache/
 * java对象继承自Object对象，空的Object对象只包含JAVA标准对象头部(Mark Word[32bit或者64bit]和Class Metadata Address[32bit或者64bit])；
 * Object object = new Object();
 * 如果是32bit的JVM，则对象头=Mark Word[32bit] + Class Metadata Address[32bit] = 64bit = 8byte，则这时object在内存中占用8byte
 * 如果是64bit的JVM，则对象头=Mark Word[64bit] + Class Metadata Address[32bit或者64bit，由开辟的JVM内存大小决定] = 96bit或者128bit= 16byte(padding)
 * PS ：JMM规定所有JAVA对象是8byte对齐的，就是说：
 * a.JVM访问的内存地址一定是8byte的整数倍。如果JVM开启的内存小于32G，则引用（地址）用32bit就足够了。
 * b.如果Class Metadata Address是32bit的，则JVM会padding剩余32bit,使其字节对齐。
 * <p>
 * 这个类用14个long作为填充位+一个long作为真实的有效数据位
 * 整个对象在内存中大小=JAVA空对象大小(8byte或者16byte) + 14个long(14*8) + 1个真正的数据long(1*8) = 16 * 8 = 128byte
 * CPU的Cache系统中的CacheLine一般为32,64,128,256等2的幂。目前主流的是64byte，这里我们用了相当于两到三个CacheLine的大小来填充数据，目的是:
 * a.防止False Sharing现象的发生；
 * b.兼容有些CPU的CacheLine为128位的情况；
 * 将真实值设置volatile类型是利用CPU内存屏障(Memory Barries)指令来保证多线程环境下value值的可见性。
 * JVM内存模型中volatile已经实现了缓存一致性机制，所以volatile变量是不可能从多个CPU的Cache系统同时写入主存的(不存在写竞争或者认为这个竞争已经被JVM内存模型上锁防止了)
 * 这个对象在32bit的JVM内存占用大小为128byte,在64bit的JVM内存占用大小为136byte。
 */
public class PaddingLong {
    public long p1, p2, p3, p4, p5, p6, p7; // cache line padding
    private volatile long value;
    public long p8, p9, p10, p11, p12, p13, p14; // cache line padding


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
