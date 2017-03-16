package com.quiet.tree;


import Jama.Matrix;
import com.quiet.data.TimeSlotNumber;
import com.quiet.data.TimestampNumber;
import com.quiet.math.LinearRegression;
import org.apache.commons.lang3.StringUtils;
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
    private static Field bandWidthRateField;


    @BeforeClass
    public static void init() throws Exception {
        allocField = Node.class.getDeclaredField("alloc");
        passField = Node.class.getDeclaredField("pass");
        rejectField = Node.class.getDeclaredField("reject");
        allowField = Node.class.getDeclaredField("allow");
        bandWidthRateField = Node.class.getDeclaredField("bandWidthRate");
        accumulationSet = new LinkedHashSet<>();
        divisibleSet = new LinkedHashSet<>();
        accumulationSet.add(allocField);
        accumulationSet.add(passField);
        accumulationSet.add(rejectField);
        divisibleSet.add(allowField);
        divisibleSet.add(bandWidthRateField);
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
                root.setDivisible("root", bandWidthRateField, ThreadLocalRandom.current().nextInt(100));
                root.backupAll();
            }
        }
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childAllocFieldAvgData = root.getChildFieldAvgData(allocField);
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childPassFieldAvgData = root.getChildFieldAvgData(passField);
        LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childRejectFieldAvgData = root.getChildFieldAvgData(rejectField);
        List<TimestampNumber> bandWidthRateList = root.getRootFieldData(bandWidthRateField);
        print(passField, childPassFieldAvgData);
        print(bandWidthRateField, bandWidthRateList);
        ml(childPassFieldAvgData, bandWidthRateList);


    }

    private void ml(LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childPassFieldAvgData, List<TimestampNumber> bandWidthRateList) {
        Collection<List<TimeSlotNumber>> childrenTimeSlotNumbers = childPassFieldAvgData.values();
        Map<Integer, List<TimeSlotNumber>> result = new LinkedHashMap<>();
        for (int i = 0; i < childrenTimeSlotNumbers.iterator().next().size(); i++) {
            result.put(i, new ArrayList<TimeSlotNumber>());
        }
        for (List<TimeSlotNumber> list : childrenTimeSlotNumbers) {
            for (int i = 0; i < list.size(); i++) {
                result.get(i).add(list.get(i));
            }
        }
        double[][] AArray = new double[bandWidthRateList.size()][];
        for (int i = bandWidthRateList.size() - 1; i >= 0; i--) {
            List<TimeSlotNumber> list = result.get(i);
            AArray[bandWidthRateList.size() - i - 1] = new double[bandWidthRateList.size()];
            for (int j = 0; j < list.size(); j++) {
                AArray[bandWidthRateList.size() - i - 1][j] = list.get(j).getNumber().doubleValue();
            }
        }

        double[][] bArray = new double[bandWidthRateList.size()][1];
        for (int i = bandWidthRateList.size() - 1; i >= 0; i--) {
            bArray[bandWidthRateList.size() - 1 - i] = new double[1];
            bArray[bandWidthRateList.size() - 1 - i][0] = bandWidthRateList.get(i).getNumber().doubleValue();
        }
        Matrix A = new Matrix(AArray);
        Matrix b = new Matrix(bArray);
        Matrix ols = LinearRegression.ols(A, b);
        Matrix ridge = LinearRegression.olsRidge(A, b, 1.0d);
        Matrix lwlr = LinearRegression.lwlrRidge(A, b, 10.0d, 100);

     /*   A.print(10,2);
        b.print(10,2);*/
/*        ols.print(10,2);
        ridge.print(10,2);*/
        lwlr.print(10, 2);
    }

    public void print(Field field, List<TimestampNumber> bandWidthRates) {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.rightPad(field.getName(), _SIZE));
        sb.append(lineSeparator);
        for (int i = bandWidthRates.size() - 1; i >= 0; i--) {
            sb.append(StringUtils.rightPad(bandWidthRates.get(i).getTimestamp() + "", _SIZE));
            sb.append(StringUtils.rightPad(bandWidthRates.get(i).getNumber().toString(), SIZE));
            sb.append(lineSeparator);
        }
        LOGGER.info(sb.toString());
    }

    public void print(Field field, LinkedHashMap<ITreeElement, List<TimeSlotNumber>> childFieldAvgData) {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.rightPad(field.getName(), _SIZE));
        for (ITreeElement treeElement : childFieldAvgData.keySet()) {
            String identifier = treeElement.getIdentifier();
            sb.append(StringUtils.rightPad(identifier, SIZE));
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

        for (int i = result.size() - 1; i >= 0; i--) {
            List<TimeSlotNumber> list = result.get(i);
            Timestamp start = new Timestamp(0);
            Timestamp end = new Timestamp(0);
            for (int j = 0; j < list.size(); j++) {
                if (start.getTime() == 0) {
                    Assert.assertTrue(end.getTime() == 0);
                    start = list.get(j).getStart();
                    end = list.get(j).getEnd();
                    sb.append(StringUtils.rightPad(start + "--" + end, _SIZE));
                }
                sb.append(StringUtils.rightPad(list.get(j).getNumber().toString(), SIZE));
            }
            sb.append(lineSeparator);
        }
        LOGGER.info(sb.toString());
    }

}
