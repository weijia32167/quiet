package com.quiet.tree;

import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 */
public interface ITreeElement<T> {

    public ElementType getType();

    public int getDepth();

    public T outputData();

    public ITreeElement<T> getRoot();

    public ITreeElement<T> getParent();

    public PriorityQueue<ITreeElement<T>> getChildren();

    public ITreeElement<T> element(ITreeElement<T> children);

    public int getPriority();

    public void setParent(ITreeElement<T> parent);

}
