package com.quiet.tree;


import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   :
 */
public class TreeElement<T> implements ITreeElement<T>,Comparable<ITreeElement<T>> {

    private T data;

    private ITreeElement<T> parent;

    private PriorityQueue<ITreeElement<T>> childrens;

    private int priority = -1;

    public TreeElement(T data) {
        this.data = data;
    }

    /*树元素不包含额外数据*/
    public TreeElement() {}

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
    public PriorityQueue<ITreeElement<T>> getChildren() {
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
    public ITreeElement<T> getRoot() {
        ElementType elementType = getType();
        if(elementType == ElementType.ROOT || elementType == ElementType.ROOT_LEAF){
            return this;
        }else{
            ITreeElement parent = getParent();
            while(true){
                if(parent != null&&parent.getParent() == null) {
                    return parent;
                }else{
                    parent = parent.getParent();
                }
            }
        }
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

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority() {
        ITreeElement parent = getParent();
        if(parent.getChildren() == null){
            priority = 1;
        }else{
            int size = parent.getChildren().size();
            priority = size+1;
        }
    }

    @Override
    public ITreeElement<T> element(ITreeElement<T> children) {
        children.setParent(this);
        children.setPriority();
        if(childrens == null){
            childrens = new PriorityQueue<>();
        }
        childrens.offer(children);
        return this;
    }

    @Override
    public int compareTo(ITreeElement<T> target) {
        if( this.getRoot() == this.getRoot() && this.getDepth() == target.getDepth() ){
            return this.priority - target.getPriority();
        }
        throw new IllegalStateException("Not same root or not same depth!");
    }

    @Override
    public void setParent(ITreeElement<T> parent) {
        if(this.parent != null){
            throw new IllegalStateException("You can't invoke function setParent()!");
        }
        this.parent = parent;
    }
}
