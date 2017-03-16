package com.quiet.tree;

import com.quiet.data.TimeSlotNumber;
import com.quiet.data.TimestampNumber;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeRootElement extends TreeNodeElement implements ITreeRoot {

    final LinkedHashMap<String, ITreeElement> treeNodeElements;

    /**
     * @param name 唯一标识符
     * @param size 元素数据备份数据量
     */
    public TreeRootElement(String name, int size, int scale) {
        super(name, size, scale);
        history = new History(size, scale);
        treeNodeElements = new LinkedHashMap<>();
        treeNodeElements.put(name,this);
    }

    @Override
    public final ITreeElement getTreeElement(String name) {
        return treeNodeElements.get(name);
    }

    public LinkedHashMap<String, ITreeElement> getTreeElements() {
        return treeNodeElements;
    }

    @Override
    public LinkedHashMap<String, ITreeElement> getLeafTreeElements() {
        LinkedHashMap<String, ITreeElement> result = null;
        if(treeNodeElements!=null){
            result = new LinkedHashMap<>();
            Set<String> nodeNames = treeNodeElements.keySet();
            for(String nodeName : nodeNames ){
                if(treeNodeElements.get(nodeName).isLeaf()){
                    result.put(nodeName,treeNodeElements.get(nodeName));
                }
            }
        }
        return result;
    }

    @Override
    public void setAllField(Set<Field> accumulationSet, Set<Field> divisibleSet) {
        if (!getStructureState()) {
            throw new IllegalStateException("Not allowed before the method work() before!");
        }
        synchronized (this) {
            Map<String, ITreeElement> elements = getTreeElements();
            for (String identifier : elements.keySet()) {
                elements.get(identifier).init(accumulationSet, divisibleSet);
            }
        }
    }

    @Override
    public void increment(String nodeIdentifier, Field field) {
        increment(nodeIdentifier, field, 1);
    }

    @Override
    public void increment(String nodeIdentifier, Field field, int number) {
        ITreeElement treeElement = getTreeElement(nodeIdentifier);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named " + nodeIdentifier);
        }else{
            List<ITreeElement> pathElements = treeElement.getPathElements();
            for (ITreeElement temp : pathElements) {
                temp.increment(field, number);
            }
        }
    }

    @Override
    public void setDivisible(String nodeIdentifier, Field field, Number value) {
        ITreeElement treeElement = getTreeElement(nodeIdentifier);
        if (treeElement == null) {
            throw new IllegalArgumentException("No Tree Node named " + nodeIdentifier);
        }else{
            treeElement.update(field, value);
        }
    }

    @Override
    public void backupAll() {
        LinkedHashMap<String, ITreeElement> elements = getTreeElements();
        ITreeElement temp;
        for (String identifier : elements.keySet()) {
            temp = elements.get(identifier);
            temp.backup();
        }
    }

    @Override
    public History getHistory(String nodeName) {
        ITreeElement treeElement = getTreeElement(nodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+nodeName);
        }else{
            return treeElement.getHistory();
        }
    }

    @Override
    public int getHistorySize() {
        return history.getSize();
    }

    @Override
    public int getHistoryScale() {
        return history.getScale();
    }


    @Override
    public AtomicInteger getAccumulationFieldValue(String nodeIdentifier, Field field) {
        checkDataState();
        ITreeElement element = getTreeElement(nodeIdentifier);
        return element.getAccumulationFieldValue(field);
    }

    @Override
    public Number getDivisibleFieldValue(String nodeIdentifier, Field field) {
        checkDataState();
        ITreeElement element = getTreeElement(nodeIdentifier);
        return element.getDivisibleFieldValue(field);
    }

    @Override
    public LinkedHashMap<ITreeElement, List<TimeSlotNumber>> getChildFieldAvgData(Field field) {
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> result = null;
        if (!getStructureState() || !getDataState()) {
            throw new IllegalStateException();
        }
        LinkedHashMap<String, ITreeElement> children = getLeafTreeElements();
        if (children != null && children.size() != 0) {
            result = new LinkedHashMap<>();
            ITreeElement temp;
            History tempHistory;
            List<TimeSlotNumber> tempFieldAvgDataList;
            for (String identifier : children.keySet()) {
                temp = children.get(identifier);
                tempHistory = temp.getHistory();
                tempFieldAvgDataList = tempHistory.getFieldAvgData(field);
                result.put(temp, tempFieldAvgDataList);
            }
        }
        return result;
    }

    @Override
    public LinkedHashMap<ITreeElement, List<TimestampNumber>> getChildFieldData(Field field) {
        LinkedHashMap<ITreeElement, List<TimestampNumber>> result = null;
        if (!getStructureState() || !getDataState()) {
            throw new IllegalStateException();
        }
        LinkedHashMap<String, ITreeElement> children = getLeafTreeElements();
        if (children != null && children.size() != 0) {
            result = new LinkedHashMap<>();
            ITreeElement temp;
            for (String identifier : children.keySet()) {
                temp = children.get(identifier);
                result.put(temp, temp.getHistory().getFieldData(field));
            }
        }
        return result;
    }

    @Override
    public List<TimeSlotNumber> getRootFieldAvgData(Field field) {
        if (!getStructureState() || !getDataState()) {
            throw new IllegalStateException();
        }
        return this.getHistory().getFieldAvgData(field);
    }

    @Override
    public List<TimestampNumber> getRootFieldData(Field field) {
        if (!getStructureState() || !getDataState()) {
            throw new IllegalStateException();
        }
        return this.getHistory().getFieldData(field);
    }
}
