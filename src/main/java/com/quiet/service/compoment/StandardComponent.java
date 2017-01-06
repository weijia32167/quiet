package com.quiet.service.compoment;

import com.quiet.lifecycle.DefaultLifeCycle;
import com.quiet.lifecycle.LifeCycleState;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/3/3
 * Desc   :
 */
public abstract class StandardComponent extends DefaultLifeCycle implements IComponent {

    private CopyOnWriteArrayList<IComponent> components;

    private Lock lock = new ReentrantLock();

    private volatile AtomicReference<ComponentState> componmentState = new AtomicReference<>(ComponentState.OFF);

    public final Collection<IComponent> getDependencyComponents(){
        return components;
    }

    @Override
    public final void addComponent (IComponent component) throws ComponentException{
        validateEmpty();
        if(lifeCycleState() == LifeCycleState.NEW && componmentState() == ComponentState.OFF){
            components.add(component);
        }else{
            ComponentException componentException = new ComponentException("Component LifeCycle State is not NEW("+this+")");
            throw componentException;
        }
    }

    @Override
    public final ComponentState componmentState () {
        return componmentState.get();
    }

    private void validateEmpty () {
        lock.lock();
        if(components == null){
            components = new CopyOnWriteArrayList<>();
        }
        lock.unlock();
    }


    @Override
    public final void doInit () {
        if(components!=null){
            for(IComponent component : components){
                component.init();
            }
        }
        initSelf();
        componmentState.set(ComponentState.OFF);
    }


    @Override
    public final void doStart () {
        if(components!=null){
            for(IComponent component : components){
                component.start();
            }
        }
        startSelf();
        componmentState.set(ComponentState.ON);
    }

    @Override
    public final void doStop () {
        if(components!=null){
            for(IComponent component : components){
                component.stop();
            }
        }
        stopSelf();
    }

    @Override
    public final void doDestory () {
        if(components!=null){
            for(IComponent component : components){
                component.destory();
            }
        }
        destorySelf();
        componmentState.set(ComponentState.OFF);
    }

    @Override
    public final boolean ok () {
        return componmentState.get() == ComponentState.ON;
    }

    protected abstract void initSelf ();
    protected abstract void startSelf ();
    protected abstract void stopSelf ();
    protected abstract void destorySelf ();

}
