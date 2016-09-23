package com.quiet.tree;

import java.util.List;

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

    public ITreeElement<T> getParent();

    public List<ITreeElement<T>> getChildren();

    public ITreeElement<T> element(ITreeElement<T> children);


}
