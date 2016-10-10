package com.quiet.tree;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeRootElement<T extends IData> extends TreeNodeElement implements IRoot {

    private final Map<String,ITreeElement> treeNodeElements;

    public TreeRootElement(T data,String name) {
        super(1.0d,name);
        super.data = data;
        treeNodeElements = new ConcurrentHashMap<>();
    }

    @Override
    public final ITreeElement getTreeElement(String name) {
        return treeNodeElements.get(name);
    }

    final void put(String name,ITreeElement node){
        ITreeElement innnerNode = treeNodeElements.get(name);
        if(innnerNode == null){
            treeNodeElements.put(name,node);
        }else{
            throw new IllegalArgumentException("Tree Inner node unique name repeat!please rename TreeNode Name!");
        }
    }
}
