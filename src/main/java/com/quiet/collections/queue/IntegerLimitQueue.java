package com.quiet.collections.queue;

import com.quiet.collections.IComputability;
import com.quiet.math.Arith;

import java.util.Iterator;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/31
 * Desc   :
 */
public class IntegerLimitQueue extends LimitQueue<Integer> implements IComputability {

    public IntegerLimitQueue(int limit) {
        super(limit);
    }

    @Override
    public double average(int scale) {
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        double sum = 0.0d;
        double result;
        Iterator<Integer> iterator= iterator();

        while(iterator.hasNext()){
            Integer value = iterator.next();
            sum = Arith.add(sum,value);
        }
        result = Arith.div(sum,size(),scale);
        return result;
    }
}
