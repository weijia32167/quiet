package com.quiet.tree;


import com.quiet.data.TimeSlotNumber;
import com.quiet.data.TimestampNumber;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 * ===============================================================
 *                             Root
 *                              |
 *               ———————————----------
 *              |               |               |
 *          Node1           Node2           Node3
 *            |               |               |
 *        ---------      ----------      ----------
 *       |        |      |        |      |       |
 *     Leaf1   Leaf2   Leaf3    Leaf4   Leaf5   Leaf6
 * ================================================================
 */
public interface ITreeRoot extends ITreeElement {

    /*********************************Tree Node*******************************************************/
    /**
     * 获取节点元素
     * @param identifier 节点唯一名
     * @return 节点元素对象
     */
    ITreeElement getTreeElement(String identifier);

    /**
     * 树上的所有节点元素
     * @return Key是节点元素的唯一名，Value是节点元素对象
     */
    LinkedHashMap<String, ITreeElement> getTreeElements();
    /**
     * 树上的所有叶子节点元素
     * @return Key是节点元素的唯一名，Value是节点元素对象
     **/
    LinkedHashMap<String, ITreeElement> getLeafTreeElements();
    /**********************************Tree Data计数器数据*******************************************************/
    void setAllField(Set<Field> accumulationSet, Set<Field> divisibleSet);

    /*计数字段自增+1*/
    void increment(String nodeIdentifier, Field field);
    /*计数字段自增+n*/
    void increment(String nodeIdentifier, Field field, int number);

    /*设置普通数值字段*/
    void setDivisible(String nodeIdentifier, Field field, int value);

    void backupAll();
    /**********************************Tree Data 历史记录************************************************************************/
    History getHistory(String nodeIdentifier);

    int getHistorySize();

    int getHistoryScale();

    LinkedHashMap<ITreeElement, List<TimeSlotNumber>> getChildFieldAvgData(Field field);

    LinkedHashMap<ITreeElement, List<TimestampNumber>> getChildFieldData(Field field);


}
