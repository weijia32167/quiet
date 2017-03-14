package com.quiet.tree;


import com.quiet.data.TimeSlotNumber;
import com.quiet.data.TimestampNumber;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/9/26
 * Desc   :
 */
public class TreeTest {

    public static final int SIZE = 10;
    public static final int _SIZE = 50;

    public static final Logger LOGGER = LoggerFactory.getLogger(TreeTest.class);
    private ITreeRoot root = new TreeRootElement("root", 8, 2);

    public static final String lineSeparator = System.getProperty("line.separator");

    private static Set<Field> accumulationSet;
    private static Set<Field> divisibleSet;
    private static Field allocField;
    private static Field passField;
    private static Field rejectField;
    private static Field allowField;

    @BeforeClass
    public static void init() throws Exception {
        allocField = Node.class.getDeclaredField("alloc");
        passField = Node.class.getDeclaredField("pass");
        rejectField = Node.class.getDeclaredField("reject");
        allowField = Node.class.getDeclaredField("allow");
        accumulationSet = new LinkedHashSet<>();
        divisibleSet = new LinkedHashSet<>();
        accumulationSet.add(allocField);
        accumulationSet.add(passField);
        accumulationSet.add(rejectField);
        divisibleSet.add(allowField);
    }

    @Test
    public void test() throws Exception {
        ITreeElement local = root.child("local");
        ITreeElement localA = local.child("localA");
        ITreeElement localB = local.child("localB");
        ITreeElement localC = local.child("localC");
        ITreeElement localD = local.child("localD");

        ITreeElement other = root.child("other");
        ITreeElement otherA = other.child("otherA");
        ITreeElement otherB = other.child("otherB");
        ITreeElement otherC = other.child("otherC");
        ITreeElement otherD = other.child("otherD");
        Map<String, ITreeElement> elements = root.getTreeElements();
        root.work();
        root.setAllField(accumulationSet, divisibleSet);
        LinkedHashMap<String, ITreeElement> treeLeafElements = root.getLeafTreeElements();
        List<String> leafNames = new ArrayList<>(treeLeafElements.keySet());

        for (int i = 0; i < 1000; i++) {
            Thread.sleep(10);
            String temp = leafNames.get(ThreadLocalRandom.current().nextInt(leafNames.size()));
            root.increment(temp, allocField);
            if (i % 3 == 0) {
                root.increment(temp, rejectField);
            } else {
                root.increment(temp, passField);
            }
            if (i % 10 == 0) {
                root.backupAll();
            }
        }
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childAllocFieldAvgData = root.getChildFieldAvgData(allocField);
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childPassFieldAvgData = root.getChildFieldAvgData(passField);
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childRejectFieldAvgData = root.getChildFieldAvgData(rejectField);
        print(allocField, childAllocFieldAvgData);
        print(passField, childPassFieldAvgData);
        print(passField, childRejectFieldAvgData);
    }

    public void print(Field field, LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childFieldAvgData) {
        StringBuffer sb = new StringBuffer();
        sb.append(_toSizeString(field.getName()));
        for (ITreeElement treeElement : childFieldAvgData.keySet()) {
            String identifier = treeElement.getIdentifier();
            sb.append(toSizeString(identifier));
        }
        sb.append(lineSeparator);
        Collection<List<TimeSlotNumber>> childrenTimeSlotNumbers = childFieldAvgData.values();

        Map<Integer, List<TimeSlotNumber>> result = new LinkedHashMap<>();
        for (int i = 0; i < childrenTimeSlotNumbers.iterator().next().size(); i++) {
            result.put(i, new ArrayList<TimeSlotNumber>());
        }
        for (List<TimeSlotNumber> list : childrenTimeSlotNumbers) {
            for (int i = 0; i < list.size(); i++) {
                result.get(i).add(list.get(i));
            }
        }

        for (int i = 0; i < result.size(); i++) {
            List<TimeSlotNumber> list = result.get(i);
            Timestamp start = new Timestamp(0);
            Timestamp end = new Timestamp(0);
            for (int j = 0; j < list.size(); j++) {
                if (start.getTime() == 0) {
                    Assert.assertTrue(end.getTime() == 0);
                    start = list.get(j).getStart();
                    end = list.get(j).getEnd();
                    sb.append(_toSizeString(start + "--" + end));
                }
                sb.append(toSizeString(list.get(j).getNumber().toString()));
            }
            sb.append(lineSeparator);
        }
        LOGGER.info(sb.toString());
    }

    public String _toSizeString(String string) {
        StringBuffer sb = new StringBuffer(_SIZE);
        sb.append(string);
        for (int i = 0; i < _SIZE - string.length(); i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public String toSizeString(String string) {
        StringBuffer sb = new StringBuffer(SIZE);
        sb.append(string);
        for (int i = 0; i < SIZE - string.length(); i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
