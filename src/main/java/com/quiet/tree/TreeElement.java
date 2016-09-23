package com.quiet.tree;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 */
public class TreeElement<T> implements ITreeElement<T> {

    private T data;

    private ITreeElement<T> parent;

    private List<ITreeElement<T>> childrens;

    public TreeElement(T data) {
        this.data = data;
    }
    /*树元素不包含额外数据*/
    public TreeElement() {

    }

    @Override
    public int getDepth() {
        int depth = 0;
        ITreeElement node = this;
        while( node.getParent()!=null){
            depth++;
            node = node.getParent();
        }
        return depth;
    }

    @Override
    public List<ITreeElement<T>> getChildren() {
        if(childrens == null || childrens.size() == 0){
            return null;
        }else{
            return childrens;
        }
    }

    @Override
    public ITreeElement<T> getParent() {
        if(parent == null){
            return null;
        }else{
            return parent;
        }
    }

    @Override
    public ITreeElement<T> element(ITreeElement<T> children) {
        if(childrens == null){
            childrens = new CopyOnWriteArrayList<>();
        }
        childrens.add(children);
        return this;
    }

    @Override
    public ElementType getType() {
        ElementType result = null;
        if(this.getParent() == null && this.getChildren() == null){
            result = ElementType.ROOT_LEAF;
        }
        if(this.getParent() == null && this.getChildren() != null){
            result = ElementType.ROOT;
        }
        if(this.getParent() != null && this.getChildren() == null){
            result = ElementType.LEAF;
        }
        if(this.getParent() != null && this.getChildren() != null){
            result = ElementType.NOROOT_NOLEAF;
        }
        assert result!=null;
        return result;
    }

    @Override
    public T outputData() {
        if(data != null){
            return null;
        }else{
            return data;
        }
    }
}
