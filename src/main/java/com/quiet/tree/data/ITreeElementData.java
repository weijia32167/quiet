package com.quiet.tree.data;


import com.quiet.tree.node.ITreeElement;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   : 1.可以拆分的数据一定要备份,拆分的数据一定是double类型
 *          2.单独的数据也要备份
 */
public interface ITreeElementData {
    /*数据可以可以按照分配(拆分)*/
    public ITreeElementData allowValue(double ratio);
    /* public Map<String,BigDecimal> allowValue(double ratio);*/


     public void clear();

     /**********************数据容器***********************************/
    public ITreeElement getContainer();

    public void setContainer(ITreeElement container);

    /*********************************************************/
    public void addValueFiled(String key,double value);

    public void addAccumulationFiled(String key,int initValue);

    public BigDecimal getValue(String key);

    public AtomicInteger getAccumulation(String key);

    public int incrementAndGet(String key);



}
