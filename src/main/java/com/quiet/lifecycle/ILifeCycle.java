package com.quiet.lifecycle;


import com.quiet.listener.EventListener;
import com.quiet.listener.IListenered;
import com.quiet.listener.StateEvent;

import java.util.EventObject;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/1/26
 * Desc   :
 */
public interface ILifeCycle extends IListenered<ILifeCycle.Listener> {

    public void init()  throws LifeCycleException;

    public void start() throws LifeCycleException;

    public void stop() throws LifeCycleException;

    public void destory() throws LifeCycleException;

    public LifeCycleState lifeCycleState();

    public boolean isStart();

    public abstract class Listener implements EventListener {

        public abstract void trigger(LifeCycleEvent event);

        @Override
        public <T extends EventObject> void trigger(T event) {
            trigger((LifeCycleEvent)event);
        }
    }

    public class LifeCycleEvent extends StateEvent<ILifeCycle,LifeCycleState> {

        private static final long serialVersionUID = 7625782425467617083L;

        public LifeCycleEvent(ILifeCycle source, LifeCycleState oldState,LifeCycleState newState, long time) {
            super(source, oldState, newState, time);
        }

        public LifeCycleEvent(ILifeCycle source, LifeCycleState oldState,LifeCycleState newState) {
            super(source, oldState, newState);
        }
    }

}
