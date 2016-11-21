package com.quiet.chain;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class IntegerFlag {

    private Integer i;

    private String name;

    private String flag;

    public IntegerFlag(Integer i, String name, String flag) {
        this.i = i;
        this.name = name;
        this.flag = flag;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("["+i+"]"+"["+name+":"+flag+"]");
        return sb.toString();
    }
}
