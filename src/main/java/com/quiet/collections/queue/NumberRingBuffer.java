package com.quiet.collections.queue;

import com.quiet.collections.IComputability;
import com.quiet.data.INumber;
import com.quiet.math.Arith;

import java.math.BigDecimal;
import java.util.List;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/10
 * Desc   :
 */
public class NumberRingBuffer<T extends INumber> extends RingBuffer<T> implements IComputability {

    public NumberRingBuffer(int capacity) {
        super(capacity);
    }

    @Override
    public BigDecimal average(int scale) {
        BigDecimal result = BigDecimal.ZERO;
        List<T> elements = list();
        if (!empty()) {
            for (int i = 0; i < elements.size(); i++) {
                result = Arith.add(result, new BigDecimal(elements.get(i).getNumber().toString()));
            }
            result = Arith.div(result, elements.size(), scale);
        }
        return result;
    }

}
