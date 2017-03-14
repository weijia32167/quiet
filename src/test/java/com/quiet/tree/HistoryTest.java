package com.quiet.tree;

import com.quiet.data.TimeSlotNumber;
import com.quiet.data.TimestampNumber;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/13
 * Desc   :
 */
public class HistoryTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(HistoryTest.class);

    private static History history;
    private static Field allocField;
    private static Field passField;
    private static Field rejectField;

    @BeforeClass
    public static void init() throws Exception {
        history = new History(8, 2);
        allocField = Node.class.getDeclaredField("alloc");
        passField = Node.class.getDeclaredField("pass");
        rejectField = Node.class.getDeclaredField("reject");
        for (int i = 0; i < 8; i++) {
            Thread.sleep(10);
            history.add(allocField, i);
        }
    }

    @Test
    public void test() {
        List<TimestampNumber> datas = history.getFieldData(allocField);
        List<TimeSlotNumber> dataAvgs = history.getFieldAvgData(allocField);
        LOGGER.info(datas.toString());
        LOGGER.info(dataAvgs.toString());
    }


}
