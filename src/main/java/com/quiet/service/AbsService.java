package com.quiet.service;



import com.quiet.lifecycle.DefaultLifeCycle;
import com.quiet.service.compoment.IComponent;
import com.quiet.service.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/2/24
 * Desc   : IService的状态机实现
 *
 *      new-->service<--->pause
 *               \
 *             stop
 */
public abstract class AbsService extends DefaultLifeCycle implements IService {

    private CopyOnWriteArrayList<IComponent> components = new CopyOnWriteArrayList<>();

    protected volatile AtomicReference<ServiceState> serviceState = new AtomicReference<>(ServiceState.NEW);

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Lock lock = new ReentrantLock();

    private Condition pauseCondition = lock.newCondition();

    private IExport export = new ServiceExport(this);

    /*记录服务暂停期间，暂停掉的请求计数*/
    private AtomicInteger pauseRequestCount = new AtomicInteger(0);

    @Override
    public final void addComponent (IComponent component) {
        if(serviceState() == ServiceState.NEW){
            components.add(component);
        }else{
            ServiceException serviceException = new ServiceException("ServiceState State is not NEW("+this+")");
            throw serviceException;
        }
    }

    /***************Service LifeCycle Impl*******************************/
    @Override
    public final void doInit () {
        try{
            switch (serviceState()){
                case NEW:
                    if(components!=null){
                        for(IComponent component : components){
                            component.init();
                        }
                    }
                    logger.debug("Service all components has init success,initSelf is invoking!");
                    initSelf();
                    logger.debug("Service init success!");
                    break;
                case SERVICE:
                    logger.warn("Service has started,invoke init is invalid!");
                    break;
                case STOP:
                    logger.warn("Service has stopped,invoke init is invalid!");
                    break;
                case PAUSE:
                    logger.warn("Service has paused,invoke init is invalid!");
                    break;
            }
        }catch (Throwable e){
            throw transferServiceException(e);
        }
    }

    @Override
    public final void doStart () throws ServiceException{
        try{
            switch (serviceState()){
                case NEW:
                    if(components!=null){
                        for(IComponent component : components){
                            component.start();
                        }
                    }
                    logger.debug("Service all components has start success,startSelf is invoking!");
                    startSelf();
                    logger.debug("Service start success!");
                    serviceState.set(ServiceState.SERVICE);
                    break;
                case SERVICE:
                    logger.warn("Service has started,invoke start invalid!");
                    break;
                case STOP:
                    logger.warn("Service has stopped,invoke start invalid!");
                    break;
                case PAUSE:
                    logger.warn("Service has paused,invoke start invalid!");
                    break;
            }
        }catch (Throwable e){
            throw transferServiceException(e);
        }
    }


    @Override
    public final void doStop () throws ServiceException{
        try{
            switch (serviceState()){
                case NEW:
                    logger.warn("Service has not started,invoke stop invalid!");
                    break;
                case SERVICE:
                    if(components!=null){
                        for(IComponent component : components){
                            component.stop();
                        }
                    }
                    logger.debug("Service all components has stop success,stopSelf is invoking!");
                    stopSelf();
                    logger.debug("Service stop success!");
                    serviceState.set(ServiceState.STOP);
                    break;
                case STOP:
                    logger.warn("Service has stopped,invoke stop invalid!");
                    break;
                case PAUSE:
                    logger.warn("Service has paused,invoke stop invalid!");
                    break;
            }
        }catch (Throwable e){
            throw transferServiceException(e);
        }

    }

    @Override
    public final void doDestory () throws ServiceException{
        try{
            switch (serviceState()){
                case NEW:
                    logger.warn("Service has not started,invoke destory invalid!");
                    break;
                case SERVICE:
                    logger.warn("Service is service,invoke destory invalid!");
                    break;
                case STOP:
                    if(components!=null){
                        for(IComponent component : components){
                            component.destory();
                        }
                    }
                    logger.debug("Service all components has destory success,destorySelf is invoking!");
                    destorySelf();
                    logger.debug("Service destory success!");
                    serviceState.set(ServiceState.STOP);
                    break;
                case PAUSE:
                    logger.warn("Service has paused,invoke destory invalid!");
                    break;
            }
        }catch (Throwable e){
            throw transferServiceException(e);
        }
    }
    /******************Service pause and resume Impl***************************/
    @Override
    public final void pause () throws ServiceException{
        ServiceException serviceException;
        try {
            lock.lock();
            ServiceState state = serviceState();
            switch (state){
                case NEW:
                     serviceException = new ServiceException("Service State is NEW("+this+"),It can't be pause!");
                     throw serviceException;
                case SERVICE:
                    serviceState.compareAndSet(ServiceState.SERVICE,ServiceState.PAUSE);
                    pauseCondition.await();
                    break;
                case PAUSE:
                    break;
                case STOP:
                    serviceException = new ServiceException("Service State is STOP("+this+"),It can't be pause!");
                    throw serviceException;
            }
        }catch (Throwable e){
            ServiceException throwable = transferServiceException(e);
            throw throwable;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public final void resume () throws ServiceException{
        ServiceException serviceException;
        try {
            lock.lock();
            ServiceState state = serviceState();
            switch (state){
                case NEW:
                    serviceException = new ServiceException("Service State is NEW("+this+"),It can't be resume!");
                    throw serviceException;
                case SERVICE:
                    break;
                case PAUSE:
                    pauseCondition.signal();
                    serviceState.compareAndSet(ServiceState.PAUSE,ServiceState.SERVICE);
                    break;
                case STOP:
                    serviceException = new ServiceException("Service State is STOP("+this+"),It can't be resume!");
                    throw serviceException;
            }
        }catch (Throwable e){
            ServiceException throwable = transferServiceException(e);
            throw throwable;
        }finally {
            lock.unlock();
        }
    }

    /***********************Service State********************************/
    @Override
    public final ServiceState serviceState(){
        return serviceState.get();
    }

    @Override
    public final boolean isServicing() {
        return serviceState() == ServiceState.SERVICE ;
    }

    @Override
    public final boolean isPause () {
        return serviceState() == ServiceState.PAUSE;
    }

    /*********************Service export API************************************/
    @Override
    public final <T> T exportAPI () throws ServiceException{
        export = getExport();
        if(!isServicing()){
            throw new ServiceException("Service State is not servicing!("+serviceState()+")");
        }
        if(export == null){
            throw new ServiceException("IExport can't be null!");
        }
        try{
            return export.exportAPI();
        }catch (Throwable e){
            throw transferServiceException(e);
        }
    }

    /**********************Export*******************************/
    protected IExport getExport(){
        return export;
    }

    /********************
     * Inner Help UnSafeUtil for transfer Throwable to ServiceException
     *********************************/
    private ServiceException transferServiceException(Throwable e){
        ServiceException serviceException = null;
        if(e instanceof ServiceException){
            serviceException = (ServiceException)e;
        }else{
            serviceException = new ServiceException(e);
        }
        return serviceException;
    }
    /**********************Paused Request Count******************************/
    @Override
    public final AtomicInteger pauseRequestCount () {
        return pauseRequestCount;
    }

    protected abstract void initSelf();

    protected abstract void startSelf();

    protected abstract void stopSelf();

    protected abstract void destorySelf();
}
