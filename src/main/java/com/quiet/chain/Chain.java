package com.quiet.chain;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/5/31
 * Desc   : Provider Template For Responsibility Chain.
 *
 *       Request And Response is parameters needed to be processed
 */
public interface Chain<Request,Response,Processor extends Handler<Request,Response>> {
    /**
     * @param chainElement will be add to tail of Chain
     * @return The index of Chain
     */
    public int add(Processor chainElement);

    /**
     * @return Current Handler
     */
    public Processor current();

    /**
     * @param exceptionHandlerStrategy Set Responsibility Chain Action while Element Handler throw Exception。
     * @see Handler#doHandler
     */
    public void setExceptionHandlerStrategy(ExceptionHandlerStrategy exceptionHandlerStrategy);

    public void setCompleteCondition(CompleteCondition<Request,Response> completeCondition);

    /**
     * The responsibility chain is responsible for calling the real processor in turn。
     * @see Handler#doHandler provider real processor
     * @param request
     * @param response
     */
    public void doHandler(Request request, Response response) throws Throwable;
}
