package com.quiet.collections;

import com.quiet.collections.queue.DoubleLimitQueue;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class LimitQueneTest {

    public static final Logger logger = LoggerFactory.getLogger(LimitQueneTest.class);

    public static final int LIMIT_SIZE = 3;

    private DoubleLimitQueue lqueue;

    public static final void init(DoubleLimitQueue queue){
        assert queue!=null;
        queue.clear();
        queue.offer(1.0);
        queue.offer(2.0);
        queue.offer(3.0);
        queue.offer(4.2);
        queue.offer(5.0);
    }

    public static final void log(DoubleLimitQueue queue){
        assert queue!=null;
        for (double value : queue.getQueue()) {
            logger.info(value+"");
        }
    }

    @Before
    public void init(){
        lqueue = new DoubleLimitQueue(LIMIT_SIZE);
    }

    @Test
    public void poll(){
        lqueue.clear();
        Double value = lqueue.poll();
        logger.info(value+"");
    }

    @Test
    public void offer(){
        lqueue.clear();
        lqueue.offer(1.0);
        lqueue.offer(2.0);
        lqueue.offer(3.0);
        lqueue.offer(4.0);
        lqueue.offer(5.0);
        log(lqueue);
        logger.info(lqueue.size()+"");
        logger.info(lqueue.getLimit()+"");
        lqueue.clear();
    }


    @Test
    public void element(){
        init(lqueue);
        logger.info(lqueue.element()+"");
        logger.info(lqueue.getFirst()+"");
        logger.info(lqueue.getLast()+"");
        logger.info(lqueue.element()+"");
        lqueue.clear();
    }

    @Test
    public void push(){
        init(lqueue);
        lqueue.push(6.0);
        log(lqueue);
        lqueue.clear();
    }

    @Test
    public void pop(){
        init(lqueue);
        logger.info(lqueue.element()+"");
        logger.info(lqueue.pop()+"");
        lqueue.add(6.0);
        logger.info(lqueue.pop()+"");
        lqueue.clear();
    }

    @Test
    public void addAll(){
        lqueue.clear();
        lqueue.add(3.0);
        Collection<Double> c = new ArrayList<>();
        c.add(1.0);
        c.add(2.0);
      /*  c.add(4.0);*/
        logger.info(lqueue.addAll(c)+"");
        lqueue.clear();
    }

    @Test
    public void iterator(){
        init(lqueue);
        Iterator<Double> iterator= lqueue.iterator();
        while(iterator.hasNext()){
            Double value = iterator.next();
            logger.info(value+"");
        }
        lqueue.clear();
    }

    @Test
    public void average(){
        init(lqueue);
        logger.info(lqueue.average(3)+"");
    }


}
