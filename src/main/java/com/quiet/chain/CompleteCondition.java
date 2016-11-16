package com.quiet.chain;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public interface CompleteCondition<Request,Response> {

    public boolean isCompleteCondition(Request request,Response response);

}
