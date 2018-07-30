package org.zjw.web.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by zhoum on 2018/3/17.
 */
@Component
public class RedisLock {


    @Autowired
    private StringRedisTemplate redisTemplate;

   

   

   
    public boolean tryLock(String key,String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获得该key最新的上锁时间,然后插入当前value时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //如果当前时间和旧的时间相等,说明
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }


   
    public void unlock(String key,String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
