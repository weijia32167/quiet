package com.quiet.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class Response {

    private boolean success;

    private List<IntegerFlag> integerFlags;

    public void setSuccess() {
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void addIntegerFlag(int i,String name,String flag){
        if(integerFlags == null){
            integerFlags = new ArrayList<>();
        }
        integerFlags.add(new IntegerFlag(i,name,flag));
    }

    public List<IntegerFlag> getIntegerFlags() {
        return integerFlags;
    }
}
