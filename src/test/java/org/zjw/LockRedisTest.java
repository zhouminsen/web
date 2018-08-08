package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.zjw.web.other.lock.LockRedis;
import org.zjw.web.other.lock.LockRedis2;
import org.zjw.web.util.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhoum on 2018/7/30.
 */
public class LockRedisTest extends BaseTest {

    final String url = "http://127.0.0.1:8080/web/lock";


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LockRedis lockRedis;

    @Autowired
    private LockRedis2 lockRedis2;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void lock() throws InterruptedException {
        String id = "123456";
        redisTemplate.opsForValue().getOperations().delete(id);

        ExecutorService executorService = Executors.newCachedThreadPool();
        int num = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
//                    ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/get?id=" + id, String.class);
//                    System.out.println(forEntity.getBody());

                    lockRedis.get(id);

                    countDownLatch.countDown();
                }
            });
            executorService.submit(thread);
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(num + "个客人下单全部完毕");
        lockRedis.getInfo(id);
    }

    @Test
    public void getInfo() {
        String id = "123456";
        System.out.println(lockRedis.getInfo(id));

//        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/getInfo?id=" + id, String.class);
//        System.out.println(forEntity.getBody());
    }

    @Test
    public void lock2() throws InterruptedException {
        String key = "zjw_123456";
        redisTemplate.opsForValue().getOperations().delete(key);
        //将库存存入redis中
        lockRedis2.setStock(key, 1000);
        //模拟并发下单客户数
        int num = 1050;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockRedis2.get(key, 1);
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        countDownLatch.await();
        System.out.println("执行完毕");
        System.out.println(lockRedis2.getInfo(key) + ",总下接单人数:" + num);
    }

    @Test
    public void getInfo2() {
        String id = "zjw_123456";
        System.out.println(lockRedis2.getInfo(id));

//        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/getInfo?id=" + id, String.class);
//        System.out.println(forEntity.getBody());
    }

}
