package com.quiet.tree;

import com.quiet.collections.queue.DoubleLimitQueue;
import com.quiet.collections.queue.IntegerLimitQuene;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   : 历史记录
 */
public class History {

    public static final int DEFAULT_SIZE =10;

    private int size = DEFAULT_SIZE;

    private Map<String,DoubleLimitQueue> divisibleQueueMap;

    private Map<String,IntegerLimitQuene> accumulationQueueMap;

    public History(int size) {
      this.size = size;
        divisibleQueueMap = new HashMap<>();
        accumulationQueueMap = new HashMap<>();
    }

    int getSize(){
         return size;
     }

    public void backupDivisible(Map<String,Double> valueMap){
        if( valueMap != null && valueMap.size()!=0 ){
            Set<String> dataKeySet = valueMap.keySet();
            for( String uniqueName : dataKeySet ){
                DoubleLimitQueue doubleLimitQueue = divisibleQueueMap.get(uniqueName);
                if(doubleLimitQueue == null){
                    doubleLimitQueue = new DoubleLimitQueue(size);
                    divisibleQueueMap.put(uniqueName,doubleLimitQueue);
                }
                doubleLimitQueue.add(valueMap.get(uniqueName));
            }
        }
    }


    public void backupAccumulation(Map<String,AtomicInteger> accumulationMap){
        if( accumulationMap != null && accumulationMap.size()!=0 ){
            Set<String> dataKeySet = accumulationMap.keySet();
            for( String uniqueName : dataKeySet ){
                IntegerLimitQuene integerLimitQuene = accumulationQueueMap.get(uniqueName);
                if(integerLimitQuene == null){
                    integerLimitQuene = new IntegerLimitQuene(size);
                    accumulationQueueMap.put(uniqueName,integerLimitQuene);
                }
                integerLimitQuene.add(accumulationMap.get(uniqueName).get());
            }
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("size=").append(size);
        sb.append(",accumulation=").append(accumulationQueueMap);
        sb.append(", divisible=").append(divisibleQueueMap);
        sb.append('}');
        return sb.toString();
    }
}
