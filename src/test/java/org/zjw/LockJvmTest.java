package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zjw.web.other.lock.LockJvm;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zhoum on 2018/7/30.
 */
public class LockJvmTest extends BaseTest {

    final String url = "http://127.0.0.1:8080/web/lock";

    String id = "123456";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LockJvm lockJvm;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void lock() throws InterruptedException {
        int num = 100;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockJvm.unlock(id);
                    System.out.println("完成");
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        countDownLatch.await();
        System.out.println(num + "个客人下单全部完毕");
        getInfo();

    }

    @Test
    public void getInfo() {
        System.out.println(lockJvm.getInfo("123456"));
    }

}
