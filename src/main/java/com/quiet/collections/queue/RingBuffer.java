package com.quiet.collections.queue;

import com.quiet.concurrent.cacheline.Sequence;
import com.quiet.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/17
 * Desc   : 提供一个定长的覆盖队列，后面的元素覆盖前面的元素，元素进入队列不会被删除，只会被覆盖.
 */
public class RingBuffer<Element> {

    private Sequence write;

    private Element[] elements;

    public RingBuffer(int capacity) {
        if (!NumberUtil.isTwoPow(capacity)) {
            throw new IllegalArgumentException("RingBuffer capacity must pow of 2[1,2,4,8..]");
        }
        this.elements = (Element[]) new Object[capacity];
        write = new Sequence(0);
    }

    public void in(Element element) {
        int writeIndex = Integer.valueOf(write.get() + "");
        int location = writeIndex & (elements.length - 1);
        elements[location] = element;
        write.incrementAndGet();
    }

    public List<Element> list() {
        if (empty()) {
            return null;
        } else {
            int writeIndex = Integer.valueOf(write.get() + "");
            List<Element> result = new ArrayList<>();
            if (full()) {
                for (int i = 0; i < elements.length; i++) {
                    result.add(elements[(writeIndex + i) & (elements.length - 1)]);
                }
            } else {
                for (int i = 0; i < writeIndex; i++) {
                    result.add(elements[i]);
                }
            }
            return result;
        }
    }

    public boolean empty() {
        return write.get() == 0;
    }

    public boolean full() {
        return write.get() >= elements.length;
    }

}
