package org.zjw.web.other.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.zjw.web.util.RedisLock;

import java.util.*;

/**
 * Created by zhoum on 2018/7/30.
 */
@Component
public class LockRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String LOCK_KEY = "calc";

    @Autowired
    private RedisLock redisLock;

    //商品
    static  Map<String, Integer> products = new HashMap<>();

    //库存
    static  Map<String, Integer> stock = new HashMap<>();

    //订单
    static List<String> orders = new ArrayList<>();

    volatile Integer num = 0;

    static {
        products.put("123456", 1000);
        stock.put("123456", 1000);
    }


    public void unlock(String id) {
        String key = id;
        String value = System.currentTimeMillis() + "2000";
        if (!redisLock.tryLock(key, value)) {
            return;
        }

        //失败自循重试
       /* while (!redisLock.tryLock(key, value)){

        }*/
        try {
            //查询该商品的库存
            Integer num = stock.get(id);
            if (num == 0) {
                return;
            } else {
                //模拟多个用户下单
                orders.add(id);
                //处理业务逻辑代码


                //逻辑走完,减库存
                num = num - 1;
//                System.out.println(num);
                stock.put(id, num);
            }
        } finally {
            redisLock.unlock(key, value);
        }

    }

    public String getInfo(String id) {
        return String.format("国庆活动,皮蛋粥特价,限量份:%s,还剩:%s份,该商品成功下单用户数%s人", products.get(id)
                , stock.get(id), orders.size());
    }

}
