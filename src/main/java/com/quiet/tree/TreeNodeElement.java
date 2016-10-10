package com.quiet.tree;

import com.quiet.math.Arith;

import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeNodeElement<T extends IData> implements ITreeElement,Comparable<ITreeElement> {

    private ITreeElement parent;

    private PriorityQueue<ITreeElement> childrens;

    private int priority = -1;

    private double ratio;

    protected T data;

    public TreeNodeElement(double ratio) {
        this.ratio = ratio;
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
    public PriorityQueue<ITreeElement> getChildren() {
        if(childrens == null || childrens.size() == 0){
            return null;
        }else{
            return childrens;
        }
    }

    @Override
    public ITreeElement getParent() {
        if(parent == null){
            return null;
        }else{
            return parent;
        }
    }

    @Override
    public ITreeElement getRoot() {
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
    public int getPriority() {
        return priority;
    }

    @Override
    public double getRatio() {
        return ratio;
    }

    @Override
    public ITreeElement element(ITreeElement children) {
        double accruedRatio = getChildrenAccruedRatio();
        double flag = Arith.add(accruedRatio,children.getRatio());
        if(flag <= 1.0d){
            if(childrens == null){
                childrens = new PriorityQueue<>();
            }
            ((TreeNodeElement)children).setParent(this);
            ((TreeNodeElement)children).setPriority();
            ((TreeNodeElement)children).data = this.data.allow(((TreeNodeElement)children).ratio);
            childrens.offer(children);
            return this;
        }else{
            throw new IllegalArgumentException("Childrens ratio > 1.0 is not allow!");
        }
    }

    @Override
    public T outputData() {
        return data;
    }

    @Override
    public int compareTo(ITreeElement target) {
        if( this.getRoot() == this.getRoot() && this.getDepth() == target.getDepth() ){
            return this.priority - target.getPriority();
        }
        throw new IllegalStateException("Not same root or not same depth!");
    }

    /***********************Help Function********************************************/
    private final void setParent(ITreeElement parent) {
        if(this.parent != null){
            throw new IllegalStateException("You can't invoke function setParent()!");
        }
        this.parent = parent;
    }

    private final void setPriority() {
        ITreeElement parent = getParent();
        if(parent.getChildren() == null){
            priority = 1;
        }else{
            int size = parent.getChildren().size();
            priority = size+1;
        }
    }

    private double getChildrenAccruedRatio(){
        double result = 0.0d;
        PriorityQueue<ITreeElement> childresns = getChildren();
        if(childresns != null){
            for(ITreeElement element : childresns){
                result = Arith.add(result,element.getRatio());
            }
        }
        return result;
    }
}
