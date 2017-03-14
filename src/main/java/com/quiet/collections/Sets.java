package com.quiet.collections;

import java.util.*;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/31
 * Desc   : 实现集合的子交并补
 */
public class Sets {
    /*交集:没有交集返回一个空集合，不是null*/
    public  static <T> Set<T> intersection(Collection<T> s1, Collection<T> s2) {
        Set<T> result = new HashSet<>(s1);
        result.retainAll(s2);
        return result;
    }
    /*并集*/
    public  static <T> Set<T> union(Collection<T> s1, Collection<T> s2) {
        Set<T> result = new HashSet<T>(s1);
        result.addAll(s2);
        return result;
    }

    /*补集*/
    public static <T> Set diff(Collection<T> s1, Collection<T> s2) {
        Set<T> set = new HashSet(s2);
        set.removeAll(s1);
        return set;
    }
}