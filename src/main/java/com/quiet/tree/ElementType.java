package com.quiet.tree;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 *      ROOT    LEAF
 *
 *
 */
public enum ElementType {

    ROOT(0x000000010),              /*Tree Root*/
    NOROOT_NOLEAF(0x000000000),     /*Tree Node*/
    LEAF(0x00000001),               /*Tree Leaf*/
    ROOT_LEAF(0x00000011);          /*Tree Root & Leaf*/

    private  int value;
    ElementType(int value) {
        this.value = value;
    }

}
