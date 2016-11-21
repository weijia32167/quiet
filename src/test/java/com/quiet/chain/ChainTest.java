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
            public void doHandler(List<Integer> list, Integer integer) throws Throwable {
                System.out.println(this+":"+list);
                int i = 1/0;
                System.out.println("can't invoke there！");
            }
        });

        chain.add(new NamedHandler<List<Integer>, Integer>("Two") {

            @Override
            public void doHandler(List<Integer> list, Integer integer) throws Throwable {
                list.add(2);
                list.add(3);
                System.out.println(this.toString()+":"+list);
            }
        });

        chain.add(new NamedHandler<List<Integer>, Integer>("Three") {

            @Override
            public void doHandler(List<Integer> list, Integer integer) throws Throwable {
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
        Chain<Request,Response,Handler<Request,Response>> chain = new StandardChain<>();
        chain.setCompleteCondition(new CompleteCondition<Request, Response>() {
            @Override
            public boolean isCompleteCondition(Request request, Response response) {
                return response.isSuccess();
            }
        });
        chain.add(new ConfHandler());
        chain.add(new SameHandler());
        chain.add(new RandomHandler());
        Request request = new Request();
        Response response = new Response();
        request.addConf(1);
        request.addConf(2);
        request.addConf(3);


        request.addSame(3);
        request.addSame(4);
        request.addSame(6);

        request.addLimit(3);
        request.addLimit(5);
        request.addLimit(7);

        request.addAll(2);
        request.addAll(3);
        request.addAll(6);
        request.addAll(7);
        request.addAll(8);
        try {
            chain.doHandler(request,response);
            System.out.println(response.getIntegerFlags());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }





}
