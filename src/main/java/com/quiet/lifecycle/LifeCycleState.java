package com.quiet.lifecycle;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/1/26
 * Desc   :
 */
public enum LifeCycleState {
    NEW(1, "new"),
    INITING(2, "initing"),
    INITED(3, "inited"),
    STARTING(4,"starting"),
    STARTED(5, "started"),
    STOPPING(6, "stopping"),
    STOPPED(7, "stopped"),
    DESTORYING(8, "destorying"),
    DESTORYED(9, "destoryed");

    private int code;
    private String desc;

    private LifeCycleState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

}
