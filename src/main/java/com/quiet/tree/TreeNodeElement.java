package com.quiet.tree;

import com.quiet.math.Arith;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
class TreeNodeElement implements ITreeElement,Comparable<ITreeElement> {

    private TreeNodeElement parent;

    private PriorityQueue<TreeNodeElement> childrens;

    private int priority = 0;

    private double ratio;

    private String name;

    /*普通数据*/
    private Map<String,Double> divisible;
    /*状态数据,用于累加记录状态的*/
    private Map<String,AtomicInteger> accumulation;

    protected History history;

    TreeNodeElement(double ratio,String name) {
        this.ratio = ratio;
        this.name = name;
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
    public PriorityQueue<TreeNodeElement> getChildren() {
        if(childrens == null || childrens.size() == 0){
            return null;
        }else{
            return childrens;
        }
    }

    @Override
    public TreeNodeElement getParent() {
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
    public boolean isLeaf() {
        ElementType result = getType();
        return result == ElementType.ROOT_LEAF || result == ElementType.LEAF;
    }

    @Override
    public boolean isRoot() {
        ElementType result = getType();
        return result == ElementType.ROOT_LEAF || result == ElementType.ROOT;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public double getRatio() {
        return ratio;
    }

    private TreeNodeElement child(ITreeElement child) {
        double accruedRatio = getChildrenAccruedRatio();
        double flag = Arith.add(accruedRatio,child.getRatio());
        if(flag <= 1.0d){
            if(childrens == null){
                childrens = new PriorityQueue<>();
            }
            TreeNodeElement child0 = (TreeNodeElement)child;
            child0.setParent(this);
            child0.setPriority();
            child0.history = new History(this.getRoot().history.getSize());
            this.getRoot().put(child.getUniqueName(),child0);
            childrens.offer(child0);
            return this;
        }else{
            throw new IllegalArgumentException("Childrens ratio > 1.0 is not allow!");
        }
    }


    @Override
    public TreeNodeElement child(double ratio,String nodeName) {
        ITreeElement treeElement = new TreeNodeElement(ratio,nodeName);
        return child(treeElement);
    }

    @Override
    public List<TreeNodeElement> getChain() {
        List<TreeNodeElement> chain = new ArrayList<>();
        chain.add(this);
        TreeNodeElement temp = parent;
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

    final void _backup() {
        history.backupDivisible(divisible);
        history.backupAccumulation(accumulation);
        clear();
    }

   private final void clear(){
   /*     if(divisible!=null){
            Set<String> divisibleFieldNames = divisible.keySet();
            for(String divisibleFieldName : divisibleFieldNames){
                setDivisible0(divisibleFieldName,0);
            }
        }*/
        if(accumulation!=null){
            Set<String> accumulationFieldNames = accumulation.keySet();
            for(String accumulationFieldName : accumulationFieldNames){
                accumulation.get(accumulationFieldName).set(0);
            }
        }
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
    private final void setParent(TreeNodeElement parent) {
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
        PriorityQueue<TreeNodeElement> childresns = getChildren();
        if(childresns != null){
            for(ITreeElement element : childresns){
                result = Arith.add(result,element.getRatio());
            }
        }
        return result;
    }

    int increment0(String fieldName){
        if(this.accumulation == null){
            throw new IllegalArgumentException("Accumulation Data named "+fieldName +" is't exists!");
        }else{
            AtomicInteger value = accumulation.get(fieldName);
            if(value == null){
                throw new IllegalArgumentException("Accumulation Data named "+fieldName +" is't exists!");
            }else{
                return value.incrementAndGet();
            }
        }
    }

    int getAccumulation0(String fieldName){
       return _getAccumulation0(fieldName).intValue();
    }

   private AtomicInteger _getAccumulation0(String fieldName){
        if(accumulation == null){
            throw new IllegalArgumentException("Accumulation Data named "+fieldName +" is't exists!");
        }else{
            AtomicInteger value = accumulation.get(fieldName);
            if(value == null){
                throw new IllegalArgumentException("Accumulation Data named "+fieldName +" is't exists!");
            }else{
                return value;
            }
        }
    }

    void addAccumulationFiled0(String key, int initValue) {
        if(accumulation == null){
            accumulation = new LinkedHashMap<>();
        }
        if(accumulation.get(key)!=null){
            throw new IllegalArgumentException("key repeat,please rename key!");
        }else{
            accumulation.put(key,new AtomicInteger(initValue));
        }
    }



    void setDivisible0(String fieldName, double value) {
        if(this.divisible == null){
            this.divisible = new LinkedHashMap<>();
        }
        if(this.divisible.get(fieldName)==null){
            addDivisibleFiled0(fieldName,value);
        }
        List<TreeNodeElement> chain = getChain();
        double ratio =1.0;
        for(TreeNodeElement treeNodeElement : chain){
            ratio=Arith.mul(ratio,treeNodeElement.getRatio());
        }
        this.divisible.put(fieldName,Arith.mul(value,ratio));

    }

    void addDivisibleFiled0(String fieldName, double initValue) {
        if(this.divisible == null){
            this.divisible = new LinkedHashMap<>();
        }
        if(this.divisible.get(fieldName)!=null){
            throw new IllegalArgumentException("FieldName repeat,please rename fieldName!");
        }else{
            this.divisible.put(fieldName,Arith.mul(initValue,ratio));
        }
    }


    double getDivisible0(String fieldName) {
         if(this.divisible == null){
             throw new IllegalArgumentException("FieldName named "+ fieldName +" is't exist!");
         }else{
             if(this.divisible.get(fieldName)==null){
                 throw new IllegalArgumentException("FieldName named "+ fieldName +" is't exist!");
             }else{
                 return divisible.get(fieldName);
             }
         }
     }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TreeNodeElement{");
        sb.append("name='").append(name).append('\'');
        sb.append(", priority=").append(priority);
        sb.append(", divisible=").append(divisible);
        sb.append(", accumulation=").append(accumulation);
        sb.append(", history=").append(history);
        sb.append('}');
        return sb.toString();
    }
}
