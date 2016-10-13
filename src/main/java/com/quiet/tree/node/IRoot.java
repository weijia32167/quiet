package com.quiet.tree.node;



import java.math.BigDecimal;
import java.util.Map;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public interface IRoot extends ITreeElement {

    public ITreeElement getTreeElement(String name);

    public Map<String,ITreeElement> all();
    /*累加字段获取*/
    public void increment(String uniqueNodeName,String uniqueFieldName);
    /*获取累加字段*/
    public int getAccumulation(String uniqueNodeName,String uniqueFieldName);
    /*获取普通数值字段*/
    public BigDecimal getValue(String uniqueNodeName, String uniqueFieldName);
    /*备份数据*/
    public void backupData();


}
