package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zjw.web.other.lock.LockRedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhoum on 2018/7/30.
 */
public class LockRedisTest extends BaseTest {

    final String url = "http://127.0.0.1:8080/web/lock";

    String id = "123456";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LockRedis lockRedis;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void lock() throws InterruptedException {
//        redisTemplate.opsForValue().set("1", "100");
//        redisTemplate.boundValueOps("1").increment(-1);
//        lockRedis.lock("1");
        redisTemplate.opsForValue().getOperations().delete(id);

        ExecutorService executorService = Executors.newCachedThreadPool();

        int num = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/unlock?id=" + id, String.class);
//                    System.out.println(forEntity.getBody());

//                    lockRedis.unlock(id);

                    countDownLatch.countDown();
                }
            });
            executorService.submit(thread);
        }
        countDownLatch.await();
        System.out.println(num + "个客人下单全部完毕");
        getInfo();

    }

    @Test
    public void getInfo() {
//        System.out.println(lockRedis.getInfo(id));

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/getInfo?id=" + id, String.class);
        System.out.println(forEntity.getBody());
    }

}
