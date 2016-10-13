package com.quiet.tree.node;

import com.quiet.math.Arith;
import com.quiet.tree.data.History;
import com.quiet.tree.data.ITreeElementData;
import com.quiet.tree.data.TreeElementData;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeNodeElement implements ITreeElement,Comparable<ITreeElement> {

    private ITreeElement parent;

    private PriorityQueue<ITreeElement> childrens;

    private int priority = 0;

    private double ratio;

    private String name;

    protected ITreeElementData data;

    private History history;

    public TreeNodeElement(double ratio,String name) {
        this.ratio = ratio;
        this.name = name;
        history = new History(10);
    }

    /*********************************Implements ITreeElement*****************************************/
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
    public TreeRootElement getRoot() {
        ElementType elementType = getType();
        if(elementType == ElementType.ROOT || elementType == ElementType.ROOT_LEAF){
            return (TreeRootElement) this;
        }else{
            ITreeElement parent = getParent();
            while(true){
                if(parent != null&&parent.getParent() == null) {
                    return (TreeRootElement)parent;
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
            ((TreeNodeElement)children).data = this.data.allowValue(((TreeNodeElement)children).ratio);
            ((TreeNodeElement)children).data.setContainer(children);
            this.getRoot().put(children.getUniqueName(),children);
            childrens.offer(children);
            return this;
        }else{
            throw new IllegalArgumentException("Childrens ratio > 1.0 is not allow!");
        }
    }

    @Override
    public ITreeElementData outputData() {
        return data;
    }

    @Override
    public List<ITreeElement> getChain() {
        List<ITreeElement> chain = new ArrayList<>();
        chain.add(this);
        ITreeElement temp = parent;
        while(true) {
            if (temp != null) {
                chain.add(temp);
                temp = temp.getParent();
            } else {
                return chain;
            }
        }
    }

    @Override
    public String getUniqueName() {
        return name;
    }

    final void backup() {
        history.backupValue(((TreeElementData)data).getValue());
        history.backupAccumulation(((TreeElementData)data).getAccumulationData());
    }

    /*********************Implements Comparable******************/
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

   /* @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TreeNodeElement{");
        sb.append("name='").append(name).append('\'');
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }*/

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TreeNodeElement{");
        sb.append("name='").append(name).append('\'');
        sb.append(", priority=").append(priority);
        sb.append(", data=").append(data);
        sb.append(", history=").append(history);
        sb.append('}');
        return sb.toString();
    }
}
