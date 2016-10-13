package disruptor;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class DoubleEvent
{
    private double value;

    public void set(double value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return value+"";
    }
}
