package com.quiet.count;
import java.util.concurrent.TimeUnit;

/**
 * Copyright com.quiet
 * Author : jiawei
 * Date   : 2016/5/20
 * Desc   :
 */
public abstract class AbsDurationValue<T> {

    /*时间单位:TimeUnit.NANOSECONDS*/
    protected long duration;

    /*统计计数的起始时间*/
    protected long startTime;
    /*记录最后一次访问时间*/
    protected long lastTime;

    protected int cycle;

    public AbsDurationValue (int time, TimeUnit timeUnit) {
        this(time,timeUnit,System.nanoTime());
    }

    public AbsDurationValue (int time, TimeUnit timeUnit,long startTime) {
        duration = timeUnit.toNanos(time);
        this.startTime = startTime;
        this.lastTime = startTime;
        cycle = 1;
    }

    public long getDuration(TimeUnit timeUnit){
        switch (timeUnit){
            case NANOSECONDS:
                return TimeUnit.NANOSECONDS.toNanos(duration);
            case MICROSECONDS:
                return TimeUnit.NANOSECONDS.toMicros(duration);
            case MILLISECONDS:
                return TimeUnit.NANOSECONDS.toMillis(duration);
            case SECONDS:
                return TimeUnit.NANOSECONDS.toSeconds(duration);
            case MINUTES:
                return TimeUnit.NANOSECONDS.toMinutes(duration);
            case HOURS:
                return TimeUnit.NANOSECONDS.toHours(duration);
            case DAYS:
                return TimeUnit.NANOSECONDS.toDays(duration);
            default:
                return TimeUnit.NANOSECONDS.toNanos(duration);
        }
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getStartTime() {
        return startTime;
    }


    public int getCycle() {
        return cycle;
    }



    public final T getValue(){
        int cycle = computerCycle();
        if(cycle > 0){
            updateDuration(cycle);
            reset();
        }
        return _getValue();
    }


    /*分析两次访问之间经过了多少个周期*/
    private final int computerCycle(){
        int result = 0;
        long _duration = System.nanoTime() - startTime;
        result = (int)(_duration / duration);
        return result;
    }
    /*更新访问时间信息，重新计算周期信息*/
    private synchronized final void updateDuration(int cycleCount){
        lastTime = System.nanoTime();
        startTime = startTime + duration*cycleCount;
        cycle = cycle + cycleCount;
    }

    public abstract void reset() ;

    abstract T _getValue();

}
