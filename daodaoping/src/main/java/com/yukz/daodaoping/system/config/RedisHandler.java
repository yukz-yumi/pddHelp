package com.yukz.daodaoping.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis组件
 *
 * @author yukz
 * @version V1.0
 */
@Component
public class RedisHandler {
	
    @Autowired
    private RedisTemplate redisTemplate;

    private Jedis jedis;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * list列表删除指定的元素值
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素。
     * @param k
     * @param v
     * @return
     */
    public Long listRemove(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.remove(k,0L,v);
    }

    /**
     * 获取集合list的长度
     * @param k
     * @return
     */
    public Long listSize(String k)
    {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(k);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 获取唯一Id
     *
     * @param key
     * @param hashKey
     * @param delta   增加量（不传采用1）
     * @return
     */
    public String incrementHash(String key, String hashKey, Long delta) {
        try {
            if (null == delta) {
                delta = 1L;
            }
            return String.format("%020d", redisTemplate.opsForHash().increment(key, hashKey, delta));
        } catch (Exception e) {
            //redis宕机时采用uuid的方式生成唯一id
            int randNo = UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo = -randNo;
            }
            return String.format("%020d", randNo);
        }
    }

    public boolean setNX(byte[] b1,byte[] b2)
    {
        boolean b = redisTemplate.getConnectionFactory().getConnection().setNX(b1, b2);
        return b;
    }

    /**
     * 写入redis队列,剔除重复数据
     * @param key
     * @param value
     */
    public void writeRedisQueue(final String key, Object value)
    {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.remove(key,0L,value);
        list.leftPush(key,value);
    }

    /**
     * 读取redis队列
     * @param key
     * @return
     */
    public Object readRedisQueue(final String key)
    {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        Object obj = list.rightPop(key);
        return obj;
    }

    public Jedis getJedis(){
/*        Jedis jedis = null;
        Object nativeConnection = jcf.getConnection();
        if(nativeConnection instanceof Jedis) {
            jedis = (Jedis) nativeConnection;
        }*/
       if(null == jedis)
       {
           jedis = new Jedis("localhost", 6379);
       }
        return jedis;
    }


    public void expire(String lockKey,Long num,TimeUnit t)
    {
        redisTemplate.expire(lockKey, num, t);
    }

    public RedisConnection getRedisConnection()
    {
        return redisTemplate.getConnectionFactory().getConnection();
    }
}
