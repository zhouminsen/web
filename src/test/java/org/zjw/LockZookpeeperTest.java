package org.zjw;

import org.junit.Test;
import org.zjw.web.util.ZookpeeperLock;
import org.zjw.web.util.ZookpeeperLock2;

import java.util.concurrent.CountDownLatch;

/**
 * Created by liuyang on 2017/4/20.
 */
public class LockZookpeeperTest {



    @Test
    public void test() throws InterruptedException {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ZookpeeperLock lock = new ZookpeeperLock("127.0.0.1:2181", "lock");
                    lock.lock();
                    //共享资源
                    if (lock != null)
                        lock.unlock();
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        countDownLatch.await();
        System.out.println("执行完毕");

    }
    @Test
    public void test2() throws InterruptedException {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ZookpeeperLock2 lock = new ZookpeeperLock2("127.0.0.1:2181", "lock");
                    lock.getLocks();
                    //共享资源
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        countDownLatch.await();
        System.out.println("执行完毕");

    }

}