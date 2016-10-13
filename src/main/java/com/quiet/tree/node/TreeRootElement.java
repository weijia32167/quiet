package com.quiet.tree.node;

import com.quiet.tree.data.ITreeElementData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeRootElement<T extends ITreeElementData> extends TreeNodeElement implements IRoot {

    private final Map<String,ITreeElement> treeNodeElements;

    public TreeRootElement(T data,String name) {
        super(1.0d,name);
        super.data = data;
        super.data.setContainer(this);
        treeNodeElements = new ConcurrentHashMap<>();
        treeNodeElements.put(name,this);
    }

    @Override
    public final ITreeElement getTreeElement(String name) {
        return treeNodeElements.get(name);
    }

    public Map<String, ITreeElement> all() {
        return treeNodeElements;
    }


    @Override
    public void increment(String uniqueNodeName, String uniqueFieldName) {
        ITreeElement treeElement = getTreeElement(uniqueNodeName);
        if(treeElement!=null){
            List<ITreeElement> chain = treeElement.getChain();
            ITreeElementData data = null;
            for(ITreeElement temp : chain){
                data = temp.outputData();
                data.incrementAndGet(uniqueFieldName);
            }
        }
    }

    @Override
    public void backupData() {
        if(treeNodeElements!=null){
            Set<String> nodeNameSet = treeNodeElements.keySet();
            for(String nodeName :nodeNameSet ){
                ((TreeNodeElement)(treeNodeElements.get(nodeName))).backup();
            }
        }
    }

    @Override
    public int getAccumulation(String uniqueNodeName, String uniqueFieldName) {
        ITreeElement treeElement = getTreeElement(uniqueNodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+uniqueNodeName);
        }else{
            ITreeElementData data = treeElement.outputData();
            AtomicInteger result = data.getAccumulation(uniqueFieldName);
            if(result == null){
                throw new IllegalArgumentException("Tree Node named "+uniqueNodeName+"isn't own Filed named "+uniqueFieldName);
            }else{
                return result.get();
            }
        }
    }

    @Override
    public BigDecimal getValue(String uniqueNodeName, String uniqueFieldName) {
        ITreeElement treeElement = getTreeElement(uniqueNodeName);
        if(treeElement==null){
            throw new IllegalArgumentException("No Tree Node named "+uniqueNodeName);
        }else{
            ITreeElementData data = treeElement.outputData();
            BigDecimal result = data.getValue(uniqueFieldName);
            if(result == null){
                throw new IllegalArgumentException("Tree Node named "+uniqueNodeName+"isn't own Filed named "+uniqueFieldName);
            }else{
                return result;
            }
        }
    }



    final void put(String name, ITreeElement node){
        ITreeElement innnerNode = treeNodeElements.get(name);
        if(innnerNode == null){
            treeNodeElements.put(name,node);
        }else{
            throw new IllegalArgumentException("Tree Inner node unique name repeat!please rename TreeNode Name!");
        }
    }
}
