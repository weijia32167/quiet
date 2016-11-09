package com.quiet.tree;

import com.quiet.collections.queue.DoubleLimitQueue;
import com.quiet.collections.queue.IntegerLimitQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   : 历史记录
 */
public class History {
    /**
     * 历史数据保留多少
     */
    private int size;
    /**
     * 历史均值数据保留多少
     */
    private int averageSize;
    /**
     * 历史数据
     */
    private Map<String,DoubleLimitQueue> divisibleQueueMap;

    private Map<String,IntegerLimitQueue> accumulationQueueMap;
    /**
     * 历史数据均值记录
     **/
    private Map<String,DoubleLimitQueue> divisibleAverageQueueMap;

    private Map<String,DoubleLimitQueue> accumulationAverageQueueMap;

    public History(int size,int averageSize) {
        if(size <= 0){
            throw new IllegalArgumentException("size must > 0!");
        }
        this.size = size;
        this.averageSize = averageSize;
        divisibleQueueMap = new HashMap<>();
        accumulationQueueMap = new HashMap<>();
        divisibleAverageQueueMap = new HashMap<>();
        accumulationAverageQueueMap = new HashMap<>();
    }

    int getSize(){
         return size;
     }

    int getAverageSize() {return averageSize;}

    public synchronized void backupDivisible(String name, Double value){
        DoubleLimitQueue doubleLimitQueue = divisibleQueueMap.get(name);
        if(doubleLimitQueue == null){
            doubleLimitQueue = new DoubleLimitQueue(size);
            divisibleQueueMap.put(name,doubleLimitQueue);
        }
        if(doubleLimitQueue.isFull()){
            DoubleLimitQueue averageDoubleLimitQueue = divisibleAverageQueueMap.get(name);
            if(averageDoubleLimitQueue == null){
                averageDoubleLimitQueue = new DoubleLimitQueue(averageSize);
                divisibleAverageQueueMap.put(name,averageDoubleLimitQueue);
            }
            averageDoubleLimitQueue.add(doubleLimitQueue.average(2));
        }
        doubleLimitQueue.add(value);
    }

    public void backupAccumulation(String name,Integer value){
        IntegerLimitQueue integerLimitQueue =  accumulationQueueMap.get(name);
        if(integerLimitQueue == null){
            integerLimitQueue = new IntegerLimitQueue(size);
            accumulationQueueMap.put(name,integerLimitQueue);
        }
        if(integerLimitQueue.isFull()){
            DoubleLimitQueue averageDoubleLimitQueue = accumulationAverageQueueMap.get(name);

            if(averageDoubleLimitQueue == null){
                averageDoubleLimitQueue = new DoubleLimitQueue(averageSize);
                accumulationAverageQueueMap.put(name,averageDoubleLimitQueue);
            }
            averageDoubleLimitQueue.add(integerLimitQueue.average(3));
        }
        integerLimitQueue.add(value);
    }

    public DoubleLimitQueue getDivisibleQueue(String fieldName){
        return divisibleQueueMap.get(fieldName);
    }

    public IntegerLimitQueue getAccumulationQueue(String fieldName){
        return accumulationQueueMap.get(fieldName);
    }

    public DoubleLimitQueue getDivisibleAverageQueue(String fieldName){
        return  divisibleAverageQueueMap.get(fieldName);
    }

    public DoubleLimitQueue getAccumulationAverageQueue(String fieldName){
        return  accumulationAverageQueueMap.get(fieldName);
    }




    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("size={").
                     append("History=").append(size).
                     append(",Average=").append(averageSize).
                append("}");
        sb.append(",History={").append(accumulationQueueMap).append(",");
        sb.append(divisibleQueueMap).append("}");
        sb.append(", Average={").append(divisibleAverageQueueMap).append(",");
        sb.append(accumulationAverageQueueMap).append("}");
        sb.append('}');
        return sb.toString();
    }
}
