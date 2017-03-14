package com.quiet.tree;

import com.quiet.collections.Sets;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class TreeNodeElement implements ITreeElement {

/*    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();*/

    private AtomicBoolean structureState = new AtomicBoolean(false);
    private AtomicBoolean dataState = new AtomicBoolean(false);

    private ITreeElement parent;

    private Set<ITreeElement> children;

    private String identifier;

    /*状态数据,用于累加记录状态的*/
    protected Map<Field, AtomicInteger> accumulation;

    /*普通数据*/
    protected Map<Field, Number> divisible;


    protected History history;

    TreeNodeElement(String identifier, int size, int scale) {
        this.identifier = identifier;
        accumulation = new ConcurrentHashMap<>();
        divisible = new ConcurrentHashMap<>();
        history = new History(size, scale);
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
    public Set<ITreeElement> getChildren() {
        if (children == null || children.size() == 0) {
            return null;
        }else{
            return children;
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
    public ITreeRoot getRoot() {
        ElementType elementType = getType();
        if(elementType == ElementType.ROOT || elementType == ElementType.ROOT_LEAF){
            return (ITreeRoot) this;
        }else{
            ITreeElement parent = getParent();
            while(true){
                if(parent != null&&parent.getParent() == null) {
                    return (ITreeRoot) parent;
                }else{
                    parent = parent.getParent();
                }
            }
        }
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
    public List<ITreeElement> getPathElements() {
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
    public String getIdentifier() {
        return identifier;
    }


    /**
     * 1.check identifier repeat;
     * 2.check TreeNode state;
     * 3.construct and add child element.
     */

    @Override
    public ITreeElement child(String identifier) {
        checkIdentifier(identifier);
        if (structureState.get()) {
            throw new IllegalStateException("TreeNodeElement has working![Function child() is not allow access if state is true]");
        }
        ITreeElement treeElement = new TreeNodeElement(identifier, getRoot().getHistorySize(), getRoot().getHistoryScale());
        if (children == null) {
            children = new LinkedHashSet<>();
        }
        ((TreeNodeElement) treeElement).parent = this;
        children.add(treeElement);
        ((TreeRootElement) this.getRoot()).treeNodeElements.put(identifier, treeElement);
        return treeElement;
    }

    /**
     * Check whether identifier repeat from All Tree elements.
     * if identifier repeat,throw java.lang.IllegalArgumentException;
     */
    private void checkIdentifier(String identifier) {
        ITreeRoot root = getRoot();
        if (root.getTreeElement(identifier) != null) {
            throw new IllegalArgumentException("Tree element identifier repeat!");
        }
    }


    protected final boolean getStructureState() {
        return structureState.get();
    }

    protected final boolean getDataState() {
        return dataState.get();
    }

    @Override
    public void work() {
        structureState.compareAndSet(false, true);
    }

    @Override
    public void init(Set<Field> accumulationSet, Set<Field> divisibleSet) {
        if (Sets.intersection(accumulationSet, divisibleSet).size() != 0) {
            throw new IllegalArgumentException("Tree element repeat Data Field!");
        }else{
            for (Field field : accumulationSet) {
                accumulation.put(field, new AtomicInteger());
            }
            for (Field field : divisibleSet) {
                divisible.put(field, 0);
            }
        }
        dataState.set(true);
    }

    protected void checkDataState() {
        if (!dataState.get()) {
            throw new IllegalStateException("Tree Date has not inited!");
        }
    }

    @Override
    public void increment(Field field) {
        checkDataState();
        accumulation.get(field).incrementAndGet();
    }

    @Override
    public void increment(Field field, int number) {
        checkDataState();
        accumulation.get(field).set(accumulation.get(field).get() + number);
    }

    @Override
    public void update(Field field, Number number) {
        checkDataState();
        divisible.put(field, number);
    }

    @Override
    public void backup() {
        checkDataState();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (Field field : accumulation.keySet()) {
            AtomicInteger fieldCount = accumulation.get(field);
            history.add(field, timestamp, fieldCount.get());
            fieldCount.set(0);
        }

        for (Field field : divisible.keySet()) {
            history.add(field, timestamp, divisible.get(field));
        }
    }

    @Override
    public History getHistory() {
        checkDataState();
        return history;
    }

    /**************************************************************************************************/
    public AtomicInteger getAccumulationFieldValue(Field field) {
        checkDataState();
        if (accumulation.containsKey(field)) {
            return accumulation.get(field);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Number getDivisibleFieldValue(Field field) {
        checkDataState();
        if (divisible.containsKey(field)) {
            return divisible.get(field);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /***************************************************************************************************/
    private final ElementType getType() {
        ElementType result = null;
        if (this.getParent() == null && this.getChildren() == null) {
            result = ElementType.ROOT_LEAF;
        }
        if (this.getParent() == null && this.getChildren() != null) {
            result = ElementType.ROOT;
        }
        if (this.getParent() != null && this.getChildren() == null) {
            result = ElementType.LEAF;
        }
        if (this.getParent() != null && this.getChildren() != null) {
            result = ElementType.NOROOT_NOLEAF;
        }
        assert result != null;
        return result;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
