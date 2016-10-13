package com.quiet.tree.data;

import com.quiet.math.Arith;
import com.quiet.tree.node.ITreeElement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class TreeElementData implements ITreeElementData{

    private ITreeElement treeElement;
    /*普通数据*/
    private Map<String,BigDecimal> value;
    /*状态数据,用于累加记录状态的*/
    private Map<String,AtomicInteger> accumulationData;

    public TreeElementData(Map<String,BigDecimal> value){
        this.value = value;
    }

    private TreeElementData(Map<String,BigDecimal> value,Set<String> accumulationNameSet){
        this(value);
        if(accumulationNameSet!=null){
            accumulationData = new LinkedHashMap<>();
            for(String accumulationName :accumulationNameSet ){
                accumulationData.put(accumulationName,new AtomicInteger(0));
            }
        }
    }


    public final synchronized void addAccumulationFiled(String key,int initValue){
        if(accumulationData == null){
            accumulationData = new LinkedHashMap<>();
        }
        if(accumulationData.get(key)!=null){
            throw new IllegalArgumentException("key repeat,please rename key!");
        }else{
            accumulationData.put(key,new AtomicInteger(initValue));
        }
    }

    @Override
    public final synchronized void addValueFiled(String key, double value) {
        if(this.value == null){
            this.value = new LinkedHashMap<>();
        }
        if(this.value.get(key)!=null){
            throw new IllegalArgumentException("key repeat,please rename key!");
        }else{
            this.value.put(key,new BigDecimal(Double.toString(value)));
        }
    }

    @Override
    public BigDecimal getValue(String key) {
        if(value == null){
            return null;
        }else{
            return value.get(key);
        }
    }
    @Override
    public AtomicInteger getAccumulation(String key){
        if(accumulationData == null){
            return null;
        }else{
            return accumulationData.get(key);
        }
    }

    @Override
    public int incrementAndGet(String key) {
        AtomicInteger result = getAccumulation(key);
        if(result == null){
            throw new IllegalArgumentException("No Accumulation named "+key+"!");
        }else{
            return result.incrementAndGet();
        }
    }

    @Override
    public final TreeElementData allowValue(double ratio) {
        Map<String,BigDecimal> result = null;
        if(value!=null && value.size()!=0){
            result = new HashMap<>();
            Set<String> dataKeySet = value.keySet();
            for( String uniqueName : dataKeySet ){
                BigDecimal bigDecimal = value.get(uniqueName);
                result.put(uniqueName, Arith.mul(bigDecimal,ratio));
            }
        }
        return new TreeElementData(result,accumulationData.keySet());
    }

    @Override
    public final void clear() {
        if(accumulationData!=null && accumulationData.size()!=0){
            Set<String> dataKeySet = accumulationData.keySet();
            for( String uniqueName : dataKeySet ){
                AtomicInteger accumulation = accumulationData.get(uniqueName);
                accumulation.set(0);
            }
        }
    }

    @Override
    public final void setContainer(ITreeElement container) {
        this.treeElement = container;
    }

    @Override
    public final ITreeElement getContainer() {
        return treeElement;
    }


    public Map<String, AtomicInteger> getAccumulationData() {
        return accumulationData;
    }

    public Map<String, BigDecimal> getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("{").append(accumulationData).append("}");
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
