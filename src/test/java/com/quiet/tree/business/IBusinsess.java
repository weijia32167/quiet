package com.quiet.tree.business;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/10
 * Desc   :
 */
public interface IBusinsess {

    public boolean canService(String name);

    public void addRequest(String name);

    public void addResponse(String name);

    public void backupAndClear();

}
