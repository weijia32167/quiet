package com.quiet.tree;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 */
public interface ITreeElement<T extends IData> {

    public ElementType getType();

    public int getDepth();

    public TreeRootElement getRoot();

    public ITreeElement getParent();

    public PriorityQueue<ITreeElement> getChildren();

    /*初始化顺序会决定优先级*/
    public ITreeElement element(ITreeElement children);

    public int getPriority();

    public double getRatio();

    public T outputData();

    public String getUniqueName();
    /**当前节点到根节点的路径中所有经过的节点*/
    public List<ITreeElement> getChain();


}
