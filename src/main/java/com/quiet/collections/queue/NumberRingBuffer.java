package com.quiet.collections.queue;

import com.quiet.collections.IComputability;
import com.quiet.math.Arith;

import java.math.BigDecimal;
import java.util.List;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/14
 * Desc   :
 */
public class NumberRingBuffer extends RingBuffer<Number> implements IComputability {
    public NumberRingBuffer(int capacity) {
        super(capacity);
    }

    @Override
    public Number average(int scale) {
        BigDecimal result = BigDecimal.ZERO;
        List<Number> elements = list();
        if (!empty()) {
            for (int i = 0; i < elements.size(); i++) {
                result = Arith.add(result, new BigDecimal(elements.get(i).toString()));
            }
            result = Arith.div(result, elements.size(), scale);
        }
        return result;
    }
}
