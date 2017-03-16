package com.quiet.tree;

import com.quiet.data.TimestampNumber;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/13
 * Desc   :
 */
public class Node {
    private AtomicInteger alloc;
    private AtomicInteger pass;
    private AtomicInteger reject;
    private Number allow;
    private Number bandWidthRate;
}
