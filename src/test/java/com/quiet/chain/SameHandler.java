package com.quiet.chain;

import com.quiet.collections.Sets;

import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class SameHandler extends NamedHandler<Request,Response> {

    public SameHandler() {
        super("Same");
    }

    @Override
    public void doHandler(Request request, Response response) throws Throwable {
        Set<Integer> sameSet = request.getSame();
        Set<Integer> allSet = request.getAll();
        Set<Integer> sameResult = Sets.intersection(sameSet,allSet);
        Set<Integer> limitSet = request.getLimit();

        if(sameSet!=null&& sameSet.size()!=0){
            for(Integer id : sameSet){
                if(sameResult!=null&&sameResult.contains(id)){    //命中
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

        System.out.println(sameResult);
    }
}
