package com.quiet.service.compoment;


import com.quiet.lifecycle.ILifeCycle;

import java.util.Collection;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/2/24
 * Desc   :
 */
public interface IComponent extends ILifeCycle {

    public Collection<IComponent> getDependencyComponents() ;

    /**
     * @throw  ComponentException if this state is not error!
     * @param component child components
     */
    public void addComponent(IComponent component) throws ComponentException;


    public ComponentState componmentState();

    public boolean ok();

}
