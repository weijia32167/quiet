package com.quiet.chain;

import com.quiet.collections.Sets;

import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class ConfHandler extends NamedHandler<Request,Response> {

    public ConfHandler() {
        super("Conf");
    }

    @Override
    public void doHandler(Request request, Response response) throws Throwable {
        Set<Integer> confSet = request.getConf();
        Set<Integer> allSet = request.getAll();
        Set<Integer> limitSet = request.getLimit();
        Set<Integer> confResult = Sets.intersection(confSet,allSet);
        if(confSet!=null&& confSet.size()!=0){
            for(Integer id : confSet){
                if(confResult!=null&&confResult.contains(id)){    //命中
                    if(limitSet!=null&&limitSet.contains(id)){
                        response.addIntegerFlag(id,this.getUnique(),"Limit");
                    }else{
                        response.addIntegerFlag(id,this.getUnique(),"Hit");
                    }
                }else{
                    response.addIntegerFlag(id,this.getUnique(),"Miss");
                }
            }
        }
        System.out.println(confResult);
    }
}
