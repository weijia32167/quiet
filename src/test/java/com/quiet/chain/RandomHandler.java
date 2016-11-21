package com.quiet.chain;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */

import com.quiet.collections.Sets;

import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class RandomHandler extends NamedHandler<Request,Response> {

    public RandomHandler() {
        super("Random");
    }

    @Override
    public void doHandler(Request request, Response response) throws Throwable {
        Set<Integer> confSet = request.getConf();
        Set<Integer> samefSet = request.getSame();
        Set<Integer> allSet = request.getAll();
        Set<Integer> limitSet = request.getLimit();
        Set<Integer> randomResult = Sets.diff(Sets.union(confSet,samefSet),allSet);
        if(randomResult!=null&& randomResult.size()!=0){
            for(Integer id : randomResult){
                if(limitSet!=null&&limitSet.contains(id)){
                    response.addIntegerFlag(id,this.getUnique(),"Limit");
                }else{
                    response.addIntegerFlag(id,this.getUnique(),"Hit");
                }
            }
        }
        System.out.println(randomResult);
    }

}
