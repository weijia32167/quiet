package com.quiet.chain;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/5/31
 * Desc   :
 */
public interface Handler<Request,Response> {

    public void doHandler(Request request, Response response) throws Throwable;

    public String getUnique();

}
