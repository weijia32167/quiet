package com.quiet.tree;

import java.util.Map;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public interface IRoot extends ITreeElement {

    /**
     * 获取节点元素
     * @param name 节点唯一名
     * @return 节点元素对象
     */
    public ITreeElement getTreeElement(String name);

    /**
     * 树上的所有节点元素
     * @return Key是节点元素的唯一名，Value是节点元素对象
     */
    public Map<String,TreeNodeElement> getTreeElements();
    /**
     * 树上的所有叶子节点元素
     * @return Key是节点元素的唯一名，Value是节点元素对象
     **/
    public Map<String,TreeNodeElement> getLeafTreeElements();


    /**
     * 增加累加类型的字段，特征是任何一个子节点上数据的一个增加，都会使父节点数据增加(父节点是所有子节点计数的总和)；每次只允许增加一个步长
     * 完成后可以用increment(String nodeName,String fieldName)对树上名称为nodeName的元素进行自增操作.
     * @param fieldName 字段名称
     * @param initValue 初始值
     * @throws IllegalArgumentException 当客户端重复调用此方法，确期望增加相同名称的自增元素的时候回抛出异常
     */
    public void addAccumulationFiled(String fieldName,int initValue);

    /*更新累加字段*/
    public void increment(String nodeName,String fieldName);
    /*获取累加字段*/
    public int getAccumulation(String nodeName,String fieldName);

    /**
     * 增加拆分类型的字段，特征是这种类型的数据字段只能从root元素设置，然后数据会按照预先设置的比例以此拆分数据到各个子节点
     * @param fieldName 字段名称
     * @param initValue 初始值
     * @throws IllegalArgumentException 当客户端重复调用此方法，确期望增加相同名称的自增元素的时候回抛出异常
     */
    public void addDivisibleFiled(String fieldName,double initValue);
    /*设置拆分类型数值字段*/
    public void setDivisible(String fieldName,double value);
    /*获取拆分类型数值字段*/
    public double getDivisible(String nodeName,String fieldName);

    public History getHistory(String nodeName);

    /*备份数据*/
    public void backup();

}
