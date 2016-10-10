package com.quiet.tree;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeRootElement<T extends IData> extends TreeNodeElement {

    public TreeRootElement(T data) {
        super(1.0d);
        super.data = data;
    }

}
