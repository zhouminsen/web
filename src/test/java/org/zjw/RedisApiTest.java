package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhouminsen on 2018/7/31.
 */
public class RedisApiTest extends BaseTest {

    @Autowired
    private RedisTemplate redisTemplate;

     String list_key = "list";

    @Test
    public void list() {
        redisTemplate.delete(list_key);
        redisTemplate.opsForList().leftPushAll(list_key, "1", "2", "3", "4", "5", "6", "7", "8", "9");
        System.out.println(redisTemplate.opsForList().range(list_key,0,-1));
        redisTemplate.opsForList().leftPush(list_key, "11");

        System.out.println(redisTemplate.opsForList().range(list_key,0,-1));

        redisTemplate.opsForList().leftPop("cc",5, TimeUnit.SECONDS);

    }
}
