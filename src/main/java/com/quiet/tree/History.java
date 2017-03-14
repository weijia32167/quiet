
package com.quiet.tree;

import com.quiet.data.TimeSlotNumber;
import com.quiet.data.TimestampNumber;
import com.quiet.collections.queue.NumberRingBuffer;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/10
 * Desc   :
 */
public final class History {
    /*字段历史数据*/
    private final Map<Field, NumberRingBuffer<TimestampNumber>> fieldHistory;
    /*字段历史数据均值*/
    private final Map<Field, NumberRingBuffer<TimeSlotNumber>> fieldAvgHistory;

    private final int size;

    private final int avgSize;
    /*均值的精确度*/
    private final int scale;

    public History(int size, int scale) {
        this(size, size, scale);
    }

    public History(int size, int avgSize, int scale) {
        if (size <= 0 || avgSize <= 0) {
            throw new IllegalArgumentException("History RingBuffer size <= 0!");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("History RingBuffer avg scale < 0!");
        }
        this.size = size;
        this.avgSize = avgSize;
        this.scale = scale;
        fieldHistory = new HashMap<>();
        fieldAvgHistory = new HashMap<>();
    }

    public void add(Field field, Number value) {
        add(field, new Timestamp(System.currentTimeMillis()), value);
    }

    public void add(Field field, Timestamp timestamp, Number value) {
        NumberRingBuffer dataBuffer;
        NumberRingBuffer dataAvgBuffer;
        if (!fieldHistory.containsKey(field)) {
            assert !fieldAvgHistory.containsKey(field);
            dataBuffer = new NumberRingBuffer<TimestampNumber>(size);
            dataAvgBuffer = new NumberRingBuffer<TimeSlotNumber>(avgSize);
            synchronized (field) {
                fieldHistory.put(field, dataBuffer);
                fieldAvgHistory.put(field, dataAvgBuffer);
            }
        } else {
            assert fieldAvgHistory.containsKey(field);
            dataBuffer = fieldHistory.get(field);
            dataAvgBuffer = fieldAvgHistory.get(field);
        }
        synchronized (field) {
            TimestampNumber timestampNumber = new TimestampNumber(timestamp, value);
            dataBuffer.in(timestampNumber);
            List<TimestampNumber> timestampNumberList = dataBuffer.list();
            Timestamp start = timestampNumberList.get(0).getTimestamp();
            Timestamp end = timestampNumberList.get(timestampNumberList.size() - 1).getTimestamp();
            Number average = dataBuffer.average(scale);
            TimeSlotNumber timeSlotNumber = new TimeSlotNumber(start, end, average);
            dataAvgBuffer.in(timeSlotNumber);
        }
    }

    public List<TimestampNumber> getFieldData(Field field) {
        if (field == null || fieldHistory == null || fieldHistory.get(field) == null) {
            return null;
        } else {
            return fieldHistory.get(field).list();
        }

    }

    public List<TimeSlotNumber> getFieldAvgData(Field field) {
        if (field == null || fieldAvgHistory == null || fieldAvgHistory.get(field) == null) {
            return null;
        } else {
            return fieldAvgHistory.get(field).list();
        }
    }

    int getSize() {
        return size;
    }

    int getAvgSize() {
        return avgSize;
    }

    int getScale() {
        return scale;
    }
}
