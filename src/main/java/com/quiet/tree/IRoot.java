package com.quiet.tree;

import java.util.List;
import java.util.Map;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public interface IRoot extends ITreeElement {

    /*********************************Tree Node*******************************************************/
    /**
     * 获取节点元素
     * @param name 节点唯一名
     * @return 节点元素对象
     */
    ITreeElement getTreeElement(String name);

    /**
     * 树上的所有节点元素
     * @return Key是节点元素的唯一名，Value是节点元素对象
     */
    Map<String,TreeNodeElement> getTreeElements();
    /**
     * 树上的所有叶子节点元素
     * @return Key是节点元素的唯一名，Value是节点元素对象
     **/
    Map<String,TreeNodeElement> getLeafTreeElements();
    /**********************************Tree Data计数器数据*******************************************************/
     /*累加字段清零,初始化计数器*/
    void initAccumulation(String fieldName);
    /*获取TreeNode累加字段*/
    int getAccumulation(String nodeName,String fieldName);
    /*计数字段自增+1*/
    void increment(String nodeName,String fieldName);
    /*计数字段自增+n*/
    void increment(String nodeName,String fieldName,int number);

    /**********************************Tree Data拆分数据*******************************************************/
    /*设置拆分类型数值字段*/
    void setDivisible(String fieldName,double value);
    /*获取TreeNode拆分类型数值字段*/
    double getDivisible(String nodeName,String fieldName);
    /**********************************Tree Data 历史记录************************************************************************/
    History getHistory(String nodeName);

    List<Integer> getAccumulationHistory(String nodeName,String fieldName);

    List<Double> getDivisibleHistory(String nodeName,String fieldName);

    List<Double> getAverageAccumulationHistory(String nodeName,String fieldName);

    List<Double> getAverageDivisibleHistory(String nodeName,String fieldName);
}
