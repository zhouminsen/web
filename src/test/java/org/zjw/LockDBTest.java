package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.web.other.lock.LockDB;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zhoum on 2018/7/27.
 */

public class LockDBTest extends BaseTest {

    @Autowired
    private LockDB lockDB;

    @Test
    public void lock() throws InterruptedException {
        int num = 100;
        //先插入一条初始化线程池
        lockDB.lock(1);
        CountDownLatch end = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "准备执行");
                    lockDB.lock(1);
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                    end.countDown();

                }
            });
            thread.start();
        }
        end.await();
        System.out.println("执行完毕了");
    }


    @Test
    public void lock2() throws InterruptedException {
        int num = 50;
        //先插入一条初始化线程池
        lockDB.lock(1);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "等待执行");
                        //等待所有线程一起执行
                        start.await();
                        lockDB.lock(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(Thread.currentThread().getName() + "执行完毕");
                        end.countDown();
                    }
                }
            });
            thread.start();
        }
        //休眠  让所有线程进入准备状态
        Thread.sleep(2000);
        System.out.println("开始执行了");
        start.countDown();

        end.await();
        System.out.println("执行完毕了");
    }

}
