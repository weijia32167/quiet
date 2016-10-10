package com.quiet.tree;

import com.quiet.math.Arith;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class DoubleData implements IData {

    private double value;

    public DoubleData(double value) {
        this.value = value;
    }

    @Override
    public IData allow(double ratio) {
       return new DoubleData(Arith.mul(value,ratio));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DoubleData{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
