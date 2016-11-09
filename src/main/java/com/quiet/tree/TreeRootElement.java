package com.quiet.tree;

import com.quiet.collections.queue.DoubleLimitQueue;
import com.quiet.collections.queue.IntegerLimitQueue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeRootElement extends TreeNodeElement implements IRoot {

    private final Map<String,TreeNodeElement> treeNodeElements;

    /**
     * @param name 唯一标识符
     * @param size 元素数据备份数据量
     */
    public TreeRootElement(String name,int size) {
        super(1.0d,name);
        history = new History(size,size);
        treeNodeElements = new ConcurrentHashMap<>();
        treeNodeElements.put(name,this);
    }

    @Override
    public final TreeNodeElement getTreeElement(String name) {
        return treeNodeElements.get(name);
    }

    public Map<String, TreeNodeElement> getTreeElements() {
        return treeNodeElements;
    }

    @Override
    public Map<String, TreeNodeElement> getLeafTreeElements() {
        Map<String, TreeNodeElement> result = null;
        if(treeNodeElements!=null){
            result = new HashMap<>();
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
    public void initAccumulation(String fieldName) {
        Set<String> nodeNames = treeNodeElements.keySet();
        TreeNodeElement treeNodeElement = null;
        for(String nodeName : nodeNames){
            treeNodeElement = treeNodeElements.get(nodeName);
            treeNodeElement.setAccumulation0(fieldName);
        }
    }

    @Override
    public int getAccumulation(String uniqueNodeName, String uniqueFieldName) {
        TreeNodeElement treeElement = getTreeElement(uniqueNodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+uniqueNodeName);
        }else{
            return treeElement.getAccumulation0(uniqueFieldName);
        }
    }

    @Override
    public void increment(String nodeName, String fieldName) {
        TreeNodeElement treeElement = getTreeElement(nodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+nodeName);
        }else{
            List<TreeNodeElement> chain = treeElement.getChain();
            for(TreeNodeElement temp : chain){
                temp.increment0(fieldName,1);
            }
        }
    }
    @Override
    public void increment(String nodeName, String fieldName,int number) {
        if(number <= 0){
            throw new IllegalArgumentException("number must > 0");
        }else{
            TreeNodeElement treeElement = getTreeElement(nodeName);
            if(treeElement==null){
                throw new IllegalArgumentException("No Tree Node named "+nodeName);
            }else{
                List<TreeNodeElement> chain = treeElement.getChain();
                for(TreeNodeElement temp : chain){
                    temp.increment0(fieldName,number);
                }
            }
        }
    }




    @Override
    public void setDivisible(String fieldName, double value) {
        Set<String> nodeNames = treeNodeElements.keySet();
        TreeNodeElement treeNodeElement = null;
        for(String nodeName : nodeNames){
            treeNodeElement = treeNodeElements.get(nodeName);
            treeNodeElement.setDivisible0(fieldName,value);
        }
    }

    @Override
    public double getDivisible(String nodeName, String fieldName) {
        TreeNodeElement treeElement = getTreeElement(nodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+nodeName);
        }else{
           return treeElement.getDivisible0(fieldName);
        }
    }

    @Override
    public History getHistory(String nodeName) {
        TreeNodeElement treeElement = getTreeElement(nodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+nodeName);
        }else{
            return treeElement.history;
        }
    }

    @Override
    public  List<Integer> getAccumulationHistory(String nodeName, String fieldName) {
        History history = getHistory(nodeName);
        IntegerLimitQueue quene = history.getAccumulationQueue(fieldName);
        List<Integer> result = new ArrayList<>();
        result.addAll(quene);
        return result;
    }

    @Override
    public List<Double> getDivisibleHistory(String nodeName, String fieldName) {
        History history = getHistory(nodeName);
        DoubleLimitQueue quene = history.getDivisibleQueue(fieldName);
        List<Double> result =  new ArrayList<>();
        result.addAll(quene);
        return result;
    }

    @Override
    public List<Double> getAverageAccumulationHistory(String nodeName, String fieldName) {
        History history = getHistory(nodeName);
        DoubleLimitQueue quene = history.getAccumulationAverageQueue(fieldName);
        List<Double> result = new ArrayList<>();
        result.addAll(quene);
        return result;
    }

    @Override
    public List<Double> getAverageDivisibleHistory(String nodeName, String fieldName) {
        History history = getHistory(nodeName);
        DoubleLimitQueue quene = history.getDivisibleAverageQueue(fieldName);
        List<Double> result = new ArrayList<>();
        result.addAll(quene);
        return result;
    }

    final void put(String name, TreeNodeElement node){
        ITreeElement innnerNode = treeNodeElements.get(name);
        if(innnerNode == null){
            treeNodeElements.put(name,node);
        }else{
            throw new IllegalArgumentException("Tree Inner node unique name repeat!please rename TreeNode Name!");
        }
    }
}
