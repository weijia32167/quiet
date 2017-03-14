package com.quiet.concurrent;

import redis.clients.jedis.JedisPool;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/3
 * Desc   :
 */
public class RedisDistributedAtomicInteger extends DistributedAtomicInteger {

    private JedisPool jedisPool;

    private String key;

    public RedisDistributedAtomicInteger(JedisPool jedisPool, String key, int initValue) {
        this.jedisPool = jedisPool;
        if (jedisPool.getResource().get("key") == null) {
       /*     jedisPool.getResource().scriptExists()*/
        }
    }

    public RedisDistributedAtomicInteger(JedisPool jedisPool, String key) {

    }


    public final int get() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }
}
