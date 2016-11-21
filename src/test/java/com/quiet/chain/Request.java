package com.quiet.chain;


import java.util.HashSet;
import java.util.Set;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/11/16
 * Desc   :
 */
public class Request {

    private Set<Integer> conf;

    private Set<Integer> same;

    private Set<Integer> all;

    private Set<Integer> limit;

    public void addConf(int i){
        if(conf == null){
            conf = new HashSet<>();
        }
        conf.add(i);
    }

    public void addSame(int i){
        if(same == null){
            same = new HashSet<>();
        }
        same.add(i);
    }

    public void addLimit(int i){
        if(limit == null){
            limit = new HashSet<>();
        }
        limit.add(i);
    }

    public void addAll(int i){
        if(all == null){
            all = new HashSet<>();
        }
        all.add(i);
    }

    public Set<Integer> getAll() {
        return all;
    }

    public Set<Integer> getConf() {
        return conf;
    }

    public Set<Integer> getSame() {
        return same;
    }

    public Set<Integer> getLimit() {
        return limit;
    }
}
