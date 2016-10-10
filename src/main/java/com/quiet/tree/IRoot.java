package com.quiet.tree;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public interface IRoot<T extends IData> extends ITreeElement<T>{

    public ITreeElement getTreeElement(String name);
}
