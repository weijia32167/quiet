package com.quiet.tree;

import org.junit.Test;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/26
 * Desc   :
 */
public class ITreeElementTest{

    private ITreeElement<Object> root  = new TreeElement<>(null,1);
    ITreeElement left = new TreeElement(null,1);
    ITreeElement middle = new TreeElement(null,2);
    ITreeElement right = new TreeElement(null,3);

    ITreeElement leftA = new TreeElement(null,1);
    ITreeElement middleA = new TreeElement(null,2);
    ITreeElement rightA = new TreeElement(null,3);

    @Test
    public void test(){
        root.element(left).element(right).element(middle);

        left.element(leftA).element(middleA).element(rightA);

        System.out.println(left.getType());
        System.out.println(right.getPriority());
        System.out.println(rightA.getRoot());
        System.out.println(middle.getRoot());
        System.out.println(root.getChildren());
        System.out.println(left.getChildren());
        System.out.println(left.getParent());
    }



}
