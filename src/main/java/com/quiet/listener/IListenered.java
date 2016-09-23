package com.quiet.listener;

import java.util.List;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/1/26
 * Desc   :
 */
public interface IListenered<Listener> {

    public void addListener(Listener listener);

    public void removeListener(Listener listener);

    public List<Listener> getListeners();

    public void clear();


}
