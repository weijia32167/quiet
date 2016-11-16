package com.quiet.chain;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public abstract class NamedHandler<Request,Response> implements Handler<Request,Response> {

    private String name;

    public NamedHandler(String name) {
        this.name = name;
    }

    @Override
    public String getUnique() {
        return name;
    }

    @Override
    public String toString() {
        return getUnique();
    }
}
