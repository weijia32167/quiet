package com.quiet.chain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class ChainTest {

    @Test
    public void ChainElementTest(){

        Chain<List<Integer>,Integer,Handler<List<Integer>,Integer>> chain = new StandardChain<>();
        chain.setExceptionHandlerStrategy(ExceptionHandlerStrategy.CONTINUE_NEXT_ELEMENT);
        chain.setCompleteCondition(new CompleteCondition<List<Integer>,Integer>() {
            @Override
            public boolean isCompleteCondition(List<Integer> list, Integer integer) {
                if(list.size() == 5){
                    return true;
                }else{
                    return false;
                }
            }
        });

        chain.add(new NamedHandler<List<Integer>, Integer>("One") {
            @Override
            public void doHandler(List<Integer> list, Integer integer, Chain chain) throws Throwable {
                System.out.println(this+":"+list);
                int i = 1/0;
                System.out.println("can't invoke there！");
            }
        });

        chain.add(new NamedHandler<List<Integer>, Integer>("Two") {

            @Override
            public void doHandler(List<Integer> list, Integer integer, Chain chain) throws Throwable {
                list.add(2);
                list.add(3);
                System.out.println(this.toString()+":"+list);
            }
        });

        chain.add(new NamedHandler<List<Integer>, Integer>("Three") {

            @Override
            public void doHandler(List<Integer> list, Integer integer, Chain chain) throws Throwable {
                System.out.println(this.toString()+":"+list);
            }
        });
        try {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            chain.doHandler(list,2);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void test(){

    }


}
