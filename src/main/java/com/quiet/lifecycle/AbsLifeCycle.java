package com.quiet.lifecycle;



import com.quiet.listener.EventSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/1/26
 * Desc   :
 *    new-->init ------>start <-------> stop
 *           \                           \
 *           \--------->destory<---------\
 *
 */
public abstract class AbsLifeCycle implements ILifeCycle{

    private Lock lock = new ReentrantLock();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected volatile AtomicReference<LifeCycleState> state = new AtomicReference<LifeCycleState>();

    protected EventSupport<Listener> eventSupport = new EventSupport<ILifeCycle.Listener>();

    public static final long DEFAULT_SELLP_TIME = 100;

    public AbsLifeCycle() {
        super();
        state.set(LifeCycleState.NEW);
    }

    @Override
    public final void init() throws LifeCycleException {
        try {
            lock.lock();
            LifeCycleState lifeCycleState = lifeCycleState();
            switch (lifeCycleState){
                case NEW:
                    state.compareAndSet(LifeCycleState.NEW, LifeCycleState.INITING);
                    long startTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.NEW,LifeCycleState.INITING));
                    doInit();
                    state.compareAndSet(LifeCycleState.INITING, LifeCycleState.INITED);
                    long endTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.INITING,LifeCycleState.INITED,(endTime-startTime)));
                    break;
                case INITING:
                    init();
                    break;
                case INITED:
                    break;
                default:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
            }
        }catch (Throwable throwable){
            throw new LifeCycleException(throwable);
        }
        finally{
            lock.unlock();
        }
    }

    @Override
    public final void start() throws LifeCycleException {
        try{
            lock.lock();
            LifeCycleState lifeCycleState = lifeCycleState();
            switch (lifeCycleState){
                case NEW:
                    init();
                    start();
                    break;
                case INITING:
                    sleep(DEFAULT_SELLP_TIME);
                    start();
                    break;
                case INITED:
                    state.compareAndSet(LifeCycleState.INITED, LifeCycleState.STARTING);
                    long startTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.INITED,LifeCycleState.STARTING));
                    doStart();
                    long endTime = System.currentTimeMillis();
                    state.compareAndSet(LifeCycleState.STARTING, LifeCycleState.STARTED);
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.STARTING,LifeCycleState.STARTED,(endTime - startTime)));
                    break;
                case STARTING:
                    sleep(DEFAULT_SELLP_TIME);
                    start();
                    break;
                case STARTED:
                    break;
                case STOPPING:
                    sleep(DEFAULT_SELLP_TIME);
                    start();
                    break;
                case STOPPED:
                    state.compareAndSet(LifeCycleState.STOPPED, LifeCycleState.STARTING);
                    long stoppedStartTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.STOPPED,LifeCycleState.STARTING));
                    doStart();
                    long stoppedEndTime = System.currentTimeMillis();
                    state.compareAndSet(LifeCycleState.STARTING, LifeCycleState.STARTED);
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.STARTING,LifeCycleState.STARTED,(stoppedEndTime - stoppedStartTime)));
                    break;
                case DESTORYING:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
                case DESTORYED:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
            }
        }catch (Throwable throwable){
            throw new LifeCycleException(throwable);
        }finally{
            lock.unlock();
        }
    }


    @Override
    public final void stop() throws LifeCycleException {
        try{
            lock.lock();
            LifeCycleState lifeCycleState = lifeCycleState();
            switch (lifeCycleState){
                case NEW:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
                case INITING:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
                case INITED:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                case STARTING:
                    sleep(DEFAULT_SELLP_TIME);
                    stop();
                    break;
                case STARTED:
                    state.compareAndSet(LifeCycleState.STARTED, LifeCycleState.STOPPING);
                    long startTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.STARTED,LifeCycleState.STOPPING));
                    doStop();
                    long endTime = System.currentTimeMillis();
                    state.compareAndSet(LifeCycleState.STOPPING, LifeCycleState.STOPPED);
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.STOPPING,LifeCycleState.STOPPED,(endTime - startTime)));
                    break;
                case STOPPING:
                    sleep(DEFAULT_SELLP_TIME);
                    stop();
                    break;
                case STOPPED:
                    break;
                case DESTORYING:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
                case DESTORYED:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;

            }
        }catch (Throwable throwable){
            throw new LifeCycleException(throwable);
        }finally{
            lock.unlock();
        }
    }


    @Override
    public final void destory () throws LifeCycleException {
        try{
            lock.lock();
            LifeCycleState lifeCycleState = state.get();
            switch (lifeCycleState){
                case NEW:
                    LifeCycleException.throwException(null,"Current LifeCycle State is :" + state.toString());
                    break;
                case INITING:
                    sleep(DEFAULT_SELLP_TIME);
                    destory();
                    break;
                case INITED:
                    state.compareAndSet(LifeCycleState.INITED, LifeCycleState.DESTORYING);
                    long startTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.INITED,LifeCycleState.DESTORYING));
                    doDestory();
                    long endTime = System.currentTimeMillis();
                    state.compareAndSet(LifeCycleState.DESTORYING, LifeCycleState.DESTORYED);
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.DESTORYING,LifeCycleState.DESTORYED,(endTime - startTime)));
                    break;
                case STARTING:
                    sleep(DEFAULT_SELLP_TIME);
                    destory();
                    break;
                case STARTED:
                    stop();
                    destory();
                    break;
                case STOPPING:
                    sleep(DEFAULT_SELLP_TIME);
                    destory();
                    break;
                case STOPPED:
                    state.compareAndSet(LifeCycleState.STOPPED, LifeCycleState.DESTORYING);
                    long stoppedStartTime = System.currentTimeMillis();
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.STOPPED,LifeCycleState.DESTORYING));
                    doDestory();
                    long stoppedEndTime = System.currentTimeMillis();
                    state.compareAndSet(LifeCycleState.DESTORYING, LifeCycleState.DESTORYED);
                    notifyListeners(new LifeCycleEvent(this,LifeCycleState.DESTORYING,LifeCycleState.DESTORYED,(stoppedEndTime - stoppedStartTime)));
                    break;
                case DESTORYING:
                    sleep(DEFAULT_SELLP_TIME);
                    destory();
                    break;
                case DESTORYED:
                    break;
            }
        }catch (Throwable throwable){
            throw new LifeCycleException(throwable);
        }finally{
            lock.unlock();
        }
    }

    @Override
    public final LifeCycleState lifeCycleState () {
        return state.get();
    }

    @Override
    public final boolean isStart () {
        return lifeCycleState()==LifeCycleState.STARTED;
    }

    /**********************EventSupport proxy method***********************************/
    public final void addListener(ILifeCycle.Listener listener){
        eventSupport.addListener(listener);
    }

    public final void removeListener(ILifeCycle.Listener listener){
        eventSupport.removeListener(listener);
    }

    private final void notifyListeners(final LifeCycleEvent lifeCycleEvent){
        eventSupport.notifyListeners(lifeCycleEvent);
    }

    @Override
    public final void clear () {
        eventSupport.clear();
    }

    @Override
    public final List<Listener> getListeners () {
        return eventSupport.getListeners();
    }

    /**********************getters and setters***********************************/

    public final EventSupport<Listener> getEventSupport() {
        return eventSupport;
    }

    public final void setEventSupport(EventSupport<ILifeCycle.Listener> eventSupport) {
        this.eventSupport = eventSupport;
    }
    /******************************need Override**********************************/

    public abstract void doInit() throws Throwable;

    public abstract void doStart() throws Throwable;

    public abstract void doStop() throws Throwable;

    public abstract void doDestory() throws Throwable;

    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LifeCycleException.throwException(e,"Current LifeCycle State is :" + state.toString());
        }
    }

}
