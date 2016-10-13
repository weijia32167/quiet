package com.quiet.tree.data;

import com.quiet.collections.queue.LimitQueue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class History {

    private int size;

    private Map<String,LimitQueue<BigDecimal>> valueQueueMap;

    private Map<String,LimitQueue<Integer>> accumulationQueueMap;

    public History(int size) {
      this.size = size;
        valueQueueMap = new HashMap<>();
        accumulationQueueMap = new HashMap<>();
    }

    public void backupValue(Map<String,BigDecimal> valueMap){
        if( valueMap != null && valueMap.size()!=0 ){
            Set<String> dataKeySet = valueMap.keySet();
            for( String uniqueName : dataKeySet ){
                LimitQueue<BigDecimal> limitQueue = valueQueueMap.get(uniqueName);
                if(limitQueue == null){
                    limitQueue = new LimitQueue<>(size);
                    valueQueueMap.put(uniqueName,limitQueue);
                }
                limitQueue.add(valueMap.get(uniqueName));
            }
        }
    }


    public void backupAccumulation(Map<String,AtomicInteger> accumulationMap){
        if( accumulationMap != null && accumulationMap.size()!=0 ){
            Set<String> dataKeySet = accumulationMap.keySet();
            for( String uniqueName : dataKeySet ){
                LimitQueue<Integer> limitQueue = accumulationQueueMap.get(uniqueName);
                if(limitQueue == null){
                    limitQueue = new LimitQueue<>(size);
                    accumulationQueueMap.put(uniqueName,limitQueue);
                }
                limitQueue.add(accumulationMap.get(uniqueName).get());
            }
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("size=").append(size);
        sb.append(",accumulation=").append(accumulationQueueMap);
        sb.append(", value=").append(valueQueueMap);
        sb.append('}');
        return sb.toString();
    }
}
