package disruptor;
import com.lmax.disruptor.EventFactory;
/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class DoubleEventFactory implements EventFactory<DoubleEvent>
{
    public DoubleEvent newInstance()
    {
        return new DoubleEvent();
    }
}