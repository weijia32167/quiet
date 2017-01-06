package com.quiet.service;


import com.quiet.lifecycle.ILifeCycle;
import com.quiet.service.compoment.IComponent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/2/24
 * Desc   :
 */
public interface IService extends ILifeCycle {

    public ServiceState serviceState() ;

    public void pause() throws ServiceException;

    public void resume() throws ServiceException;

    public boolean isServicing();

    public boolean isPause();

    public AtomicInteger pauseRequestCount();

    /**
     * Service Dependency other Component
     */
    public void addComponent(IComponent component) throws ServiceException;

    /**
     * Export Servcie API
     * @See com.sohu.tv.cdn.core.service.ServiceAPI
     */
    public <T> T exportAPI() throws ServiceException;
}
