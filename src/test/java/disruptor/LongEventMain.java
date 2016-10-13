package disruptor;
import com.lmax.disruptor.*;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   :
 */
public class LongEventMain {

    public static void main(String[] args) throws Exception {
        final RingBuffer<DoubleEvent> ringBuffer = RingBuffer.createSingleProducer(new DoubleEventFactory(), 4, new BlockingWaitStrategy());
        final SequenceBarrier barrier = ringBuffer.newBarrier();
        final BatchEventProcessor<DoubleEvent> batchEventProcessor = new BatchEventProcessor<>(ringBuffer, barrier, new EventHandler<DoubleEvent>() {
            @Override
            public void onEvent(DoubleEvent doubleEvent, long l, boolean b) throws Exception {
               /* System.out.println(ringBuffer.hasAvailableCapacity(3) );*/
            }
        });
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
        new Thread(batchEventProcessor).start();


        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long index;
                            for(int i = 1 ; i <= 100 ; i++){
                                Thread.sleep(1000);
                                index = ringBuffer.next();//申请下一个缓冲区Slot
                                ringBuffer.get(index).set((long) i);//对申请到的Slot赋值
                                ringBuffer.publish(index);//发布，然后消费者可以读到
                                System.out.println("index:"+index+":"+ringBuffer.remainingCapacity());
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
       /* new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        long readIndex = Sequencer.INITIAL_CURSOR_VALUE;
                        while(true){
                            long nextIndex = readIndex + 1;//当前读取到的指针+1，即下一个该读的位置
                            try {
                                long availableIndex = barrier.waitFor(nextIndex);//等待直到上面的位置可读取
                                while ( nextIndex <= availableIndex )//从下一个可读位置到目前能读到的位置(Batch!)
                                {

                                    DoubleEvent value = ringBuffer.get( ringBuffer.getMinimumGatingSequence());//获得Buffer中的对象
                                   // System.out.println(value);
                                    nextIndex ++;
                                }
                                readIndex = availableIndex;//刷新当前读取到的位置
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }



                    }
                }
        ).start();
*/


    }
}
