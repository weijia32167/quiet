package com.quiet.chain;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2015/12/25
 * Desc   :
 */
public class StandardChain<Request,Response,Handler extends com.quiet.chain.Handler<Request,Response>>
        extends NamedHandler<Request,Response>
        implements Chain<Request,Response,Handler> {

    private List<Handler> elements = new CopyOnWriteArrayList<>();

    private AtomicInteger index = new AtomicInteger(0);

    private ExceptionHandlerStrategy exceptionHandlerStrategy = ExceptionHandlerStrategy.BREAK_THROW_EXCEPTION;

    private CompleteCondition completeCondition;

    public StandardChain() {
        super();
    }

    public StandardChain(String name) {
        super(name);
    }

    @Override
    public void setExceptionHandlerStrategy(ExceptionHandlerStrategy exceptionHandlerStrategy) {
        if(exceptionHandlerStrategy == null){
            this.exceptionHandlerStrategy = ExceptionHandlerStrategy.BREAK_THROW_EXCEPTION;
        }else{
            this.exceptionHandlerStrategy = exceptionHandlerStrategy;
        }
    }

    @Override
    public void setCompleteCondition(CompleteCondition completeCondition) {
        this.completeCondition = completeCondition;
    }

    @Override
    public int add (Handler handler) {
        elements.add(handler);
        return elements.size()-1;
    }

    @Override
    public Handler current () {
        return elements.get(index.get());
    }

    @Override
    public void doHandler (Request request, Response response) throws Throwable{
        while(index.get() < elements.size()){
            Handler handler = current();
            index.getAndIncrement();
            try{
                handler.doHandler(request,response);
                if(completeCondition!=null){
                    if(completeCondition.isCompleteCondition(request,response)){
                        break;
                    }
                }
            }catch (Throwable e){
                switch (exceptionHandlerStrategy){
                    case CONTINUE_NEXT_ELEMENT:
                        continue;
                    case BREAK_THROW_EXCEPTION:
                        throw e;
                }
            }
        }
    }



}
