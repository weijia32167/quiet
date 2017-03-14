package com.quiet.jedis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/3/3
 * Desc   :
 */
public class JedisTest {

    public static final Logger logger = LoggerFactory.getLogger(JedisTest.class);

    JedisPool pool;
    Jedis jedis;

    @Before
    public void setUp() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(1000 * 100);
        jedisPoolConfig.setTestOnBorrow(true);
        pool = new JedisPool(jedisPoolConfig, "10.17.21.170", 6379);
        jedis = pool.getResource();
    }

    @Test
    public void exist() {
        jedis.del("512_");
        Assert.assertFalse(jedis.exists("512_1_request"));
    }


    @Test
    public void incr() {
        long value = jedis.incr("512_1_request");
        logger.info(value + "");
    }

    @Test
    public void get() {
        TestObject object = new TestObject(0, 0);
        long value = jedis.hset("512", "vod_req", "1");
        logger.info(value + "");
    }

    @Test
    public void hash() {
       /* for(int i = 1 ; i <=10 ;i++){                        //模拟10个CDN节点
            for(int j = 1 ; j < 9 ; j++){                    //模拟8个优先级
                jedis.hset(i+"_"+j,"alloc",0+"");
                jedis.hset(i+"_"+j,"pass",0+"");
                jedis.hset(i+"_"+j,"reject",0+"");
            }
        }

        for(int i = 0 ; i < 10000 ; i++){
            int randomCDNId = 1+ThreadLocalRandom.current().nextInt(10);
            int randomPriority = 1+ThreadLocalRandom.current().nextInt(8);
            int randomNumber = 1+ThreadLocalRandom.current().nextInt(100);
            jedis.hincrBy(randomCDNId+"_"+randomPriority,"alloc" ,1);
            if(randomNumber < 10){
                jedis.hincrBy(randomCDNId+"_"+randomPriority,"pass" ,1);
            }else{
                jedis.hincrBy(randomCDNId+"_"+randomPriority,"reject" ,1);
            }
        }*/
        logger.info(jedis.hget(1 + "_" + 1, "alloc"));
        logger.info(jedis.hget(1 + "_" + 1, "pass"));
        logger.info(jedis.hget(1 + "_" + 1, "reject"));
      /*  Set<String> keys = jedis.keys("1_*");*/
    }


}
