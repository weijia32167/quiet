package com.quiet.tree.business;

import com.quiet.math.Arith;
import com.quiet.tree.IData;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class DoubleData implements IData {

    private double value;

    private AtomicInteger request = new AtomicInteger(0);
    private AtomicInteger response = new AtomicInteger(0);

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

    public void addRequest(){
        request.incrementAndGet();
    }

    public void addResponse(){
        response.incrementAndGet();
    }

    public int getRequest(){
        return request.get();
    }
    public int getResponse(){
        return response.get();
    }

    public boolean canService(){
        if(response.get() > 100){
            return false;
        }else{
            return true;
        }
    }

    public void clear(){
        request.set(0);
        response.set(0);
    }

    public void backup(){

    }

}
