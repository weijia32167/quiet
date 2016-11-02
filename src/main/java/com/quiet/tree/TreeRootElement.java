package com.quiet.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        history = new History(size);
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
    public final synchronized void addAccumulationFiled(String key, int initValue) {
        Set<String> nodeNames = treeNodeElements.keySet();
        for(String nodeName : nodeNames){
            TreeNodeElement treeNodeElement = treeNodeElements.get(nodeName);
            treeNodeElement.addAccumulationFiled0(key,initValue);
        }
    }

    @Override
    public final synchronized void addDivisibleFiled(String key, double initValue) {
        Set<String> nodeNames = treeNodeElements.keySet();
        for(String nodeName : nodeNames){
            TreeNodeElement treeNodeElement = treeNodeElements.get(nodeName);
            treeNodeElement.addDivisibleFiled0(key,initValue);
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
                temp.increment0(fieldName);
            }
        }
    }

    @Override
    public void backup() {
        Set<String> nodeNameSet = treeNodeElements.keySet();
        for( String nodeName : nodeNameSet ){
            treeNodeElements.get(nodeName)._backup();
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

    final void put(String name, TreeNodeElement node){
        ITreeElement innnerNode = treeNodeElements.get(name);
        if(innnerNode == null){
            treeNodeElements.put(name,node);
        }else{
            throw new IllegalArgumentException("Tree Inner node unique name repeat!please rename TreeNode Name!");
        }
    }
}
