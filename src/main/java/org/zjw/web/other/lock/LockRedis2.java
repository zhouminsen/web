package org.zjw.web.other.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.zjw.web.util.RedisLock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoum on 2018/7/30.
 */
@Component
public class LockRedis2 {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    int stock;

    public LockRedis2() {
    }

    public void setStock(String key, int num) {
        redisTemplate.opsForValue().set(key, num + "");
        redisTemplate.opsForValue().set("guest", 0 + "");
        stock = num;
    }

    public void get(String id, int count) {
        String o = redisTemplate.opsForValue().get(id);
        if (o == null) {
            return;
        }
        int num = Integer.parseInt(o);
        if (num < count) {
            System.out.println("库存不足");
            return;
        }

        //redis increment本身具有原子性特性
        Long increment = redisTemplate.boundValueOps(id).increment(-count);

        if (increment >= 0) {
            System.out.println("抢购成功");
            //抢购成功需要把客户数累加
            redisTemplate.boundValueOps("guest").increment(1);
            //处理逻辑
        } else {
            //库存不足,需要把刚才减去的库存,添加回去
            redisTemplate.boundValueOps(id).increment(count);
            System.out.println("库存不足");
        }
    }

    public String getInfo(String id) {
        return String.format("国庆活动,皮蛋粥特价,限量份:%s,还剩:%s份,该商品成功下单用户数%s人", stock
                , redisTemplate.opsForValue().get(id), redisTemplate.opsForValue().get("guest"));
    }

}
