package com.quiet.collections;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class SetsTest {
    @Test
    public void test(){
        Set<Integer> set1 = new LinkedHashSet<>();
        Set<Integer> set2 = new LinkedHashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);

        set2.add(3);
        set2.add(4);
        set2.add(5);
        set2.add(6);

        System.out.println(set1);
        System.out.println(set2);

        System.out.println("交集:"+Sets.intersection(set1,set2));
        System.out.println("并集:"+Sets.union(set1,set2));
        System.out.println("补集:"+Sets.diff(set1,Sets.union(set1,set2)));
        System.out.println("补集:"+Sets.diff(set2,Sets.union(set1,set2)));

    }
}
