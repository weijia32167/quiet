package com.quiet.tree.business;

import com.quiet.tree.IData;
import com.quiet.tree.ITreeElement;
import com.quiet.tree.TreeRootElement;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public class BusinessRoot extends TreeRootElement implements IBusinsess{

    public BusinessRoot(IData data, String name) {
        super(data, name);
    }

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();


    @Override
    public void addResponse(String name) {
        ITreeElement treeElement = getTreeElement(name);
        if(treeElement!=null){
           List<ITreeElement> chain = treeElement.getChain();
            DoubleData data = null;
           for(ITreeElement temp : chain){
               data = (DoubleData)temp.outputData();
               data.addResponse();
           }
        }
    }

    public int getRequest(String name){
        ITreeElement treeElement = getTreeElement(name);
        if(treeElement!=null){
            DoubleData data = (DoubleData)treeElement.outputData();
            return data.getRequest();
        }else{
            return 0;
        }
    }
    public int getResponse(String name){
        ITreeElement treeElement = getTreeElement(name);
        if(treeElement!=null){
            DoubleData data = (DoubleData)treeElement.outputData();
            return data.getResponse();
        }else{
            return 0;
        }
    }

    @Override
    public void addRequest(String name) {
        ITreeElement treeElement = getTreeElement(name);
        if(treeElement!=null){
            List<ITreeElement> chain = treeElement.getChain();
            DoubleData data = null;
            for(ITreeElement temp : chain){
                data = (DoubleData)temp.outputData();
                data.addRequest();
            }
        }
    }

    @Override
    public boolean canService(String name) {
        ITreeElement treeElement = getTreeElement(name);
        if(treeElement!=null){
            DoubleData data = (DoubleData)treeElement.outputData();
            return data.canService();
        }
        return false;
    }

    @Override
    public void backupAndClear() {

    }
}
