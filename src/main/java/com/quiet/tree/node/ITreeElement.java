package com.quiet.tree.node;

import com.quiet.tree.data.ITreeElementData;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 */
public interface ITreeElement {

    public ElementType getType();

    public int getDepth();

    public TreeRootElement getRoot();

    public ITreeElement getParent();

    public PriorityQueue<ITreeElement> getChildren();

    /*初始化顺序会决定优先级*/
    public ITreeElement element(ITreeElement children);

    public int getPriority();

    public double getRatio();

    public ITreeElementData outputData();

    public String getUniqueName();
    /**当前节点到根节点的路径中所有经过的节点*/
    public List<ITreeElement> getChain();

    /*public void backup();*/


}
