package com.quiet.listener;

import java.util.EventObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/1/26
 * Desc   :
 */
public final class EventSupport<T extends EventListener> implements IListenered<T>{

    protected List<T> listeners;
    /**
     * 同步：使用调用notifyListeners方法的线程去通知监听器
     * 异步：使用自身的线程池中的一个区通知监听器
     */
    private boolean syn;

    private static ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);

    public EventSupport(){
        this(true);
    }

    public EventSupport(boolean syn){
        listeners = new CopyOnWriteArrayList<>();
        this.syn = syn;
    }

    public void notifyListeners(final EventObject event){
        if(syn){
            notifyListenersReal(event);
        }else{
            pool.submit(new Runnable() {
                @Override
                public void run () {
                    notifyListenersReal(event);
                }
            });
        }
    }

    private void notifyListenersReal(EventObject event){
        for(int i = 0 ; i < listeners.size() ; i++ ){
            EventListener listener = listeners.get(i);
            listener.trigger(event);
        }
    }


    @Override
    public void addListener(T t){
        listeners.add(t);
    }

    @Override
    public void removeListener(T t){
        listeners.remove(t);
    }

    @Override
    public List<T> getListeners() {
        return listeners;
    }

    @Override
    public void clear () {
        listeners.clear();
    }
}
