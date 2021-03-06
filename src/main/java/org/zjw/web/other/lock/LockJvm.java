package org.zjw.web.other.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhoum on 2018/7/27.
 */
@Component
public class LockJvm {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String LOCK_KEY = "calc";

    //商品
    static Map<String, Integer> products = new HashMap<>();

    //库存
    static Map<String, Integer> stock = new HashMap<>();

    //订单
    static Map<String, String> orders = new HashMap<>();

    static {
        products.put("123456", 1000);
        stock.put("123456", 1000);
    }


    public synchronized void unlock(String id) {
        //查询该商品的库存
        Integer num = stock.get(id);
        if (num == 0) {
            return;
        } else {
            //模拟多个用户下单
            orders.put(UUID.randomUUID().toString(), id);
            //处理业务逻辑代码


            //逻辑走完,减库存
            num = num - 1;
            stock.put(id, num);
        }
    }

    public String getInfo(String id) {
        return String.format("国庆活动,皮蛋粥特价,限量份:%s,还剩:%s份,该商品成功下单用户数%s人", products.get(id)
                , stock.get(id), orders.size());
    }
}
