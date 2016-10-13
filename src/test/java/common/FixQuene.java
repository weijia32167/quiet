package common;


import com.lmax.disruptor.FixedSequenceGroup;
import com.quiet.collections.queue.LimitQueue;
import org.junit.Test;

import java.util.Collection;
import java.util.Queue;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class FixQuene {

    @Test
    public void test(){
        LimitQueue<String> lqueue = new LimitQueue<String>(3);
        lqueue.offer("1");
        lqueue.offer("2");
        lqueue.offer("3");
        lqueue.offer("4");
        lqueue.offer("1");

        //1因超出队列大小限制已自动出队,输出结果为2,3,4
        for (String string : lqueue.getQueue()) {
            System.out.println(string);
        }
    }

}
