package com.quiet.tree;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 */
public interface ITreeElement {

    ElementType getType();

    boolean isRoot();

    boolean isLeaf();

    int getDepth();

    TreeRootElement getRoot();

    TreeNodeElement getParent();

    PriorityQueue<TreeNodeElement> getChildren();

    /*初始化顺序会决定优先级*/
    TreeNodeElement child(double ratio,String nodeName);

    int getPriority();

    double getRatio();

    String getUniqueName();
    /**当前节点到根节点的路径中所有经过的节点*/
    List<TreeNodeElement> getChain();

}
