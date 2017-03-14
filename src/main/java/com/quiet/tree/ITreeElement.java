package com.quiet.tree;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/21
 * Desc   : Contains tree element attributes and data attributes
 */
public interface ITreeElement {
    /*************************** Read Tree element structure and attributes*********************************************/
    /**
     * @return Unique identifier for the current element.
     */
    String getIdentifier();

    /**
     * @return Whether the current element is the root.
     */
    boolean isRoot();

    /**
     * @return Whether the current element is the leaf.
     */
    boolean isLeaf();

    /**
     * @Return The depth of the current element in the tree.
     */
    int getDepth();

    /**
     * @return The Tree root element in the Tree.
     */
    ITreeRoot getRoot();

    /**
     * @return Father element of the current element.
     */
    ITreeElement getParent();

    /**
     * @return Children elements of current element.
     */
    Set<ITreeElement> getChildren();

    /**
     * @return A set of path elements from the current element to the root element.
     */
    List<ITreeElement> getPathElements();


    /****************************** Write Tree element structure and attributes ****************************************/
    /**
     * @param identifier Tree Element identifier.
     * @return Current element
     */
    ITreeElement child(String identifier);

    void work();

    /***********************************
     * Write tree element date
     *******************************************************/

    void init(Set<Field> accumulationSet, Set<Field> divisibleSet);

    void increment(Field field);

    void increment(Field field, int number);

    void backup();

    void update(Field field, Number number);

    History getHistory();


}
