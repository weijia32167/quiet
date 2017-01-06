package com.quiet.service.export;
import com.quiet.service.IService;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/3/8
 * Desc   :
 */
public interface IExport {

    public IService getService();

    public <T> T exportAPI() throws Throwable;
}
