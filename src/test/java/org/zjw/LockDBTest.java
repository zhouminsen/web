package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.web.other.lock.LockDB;

/**
 * Created by zhoum on 2018/7/27.
 */

public class LockDBTest extends BaseTest {

    @Autowired
    private LockDB lockDB;

    @Test
    public void lock() {
        lockDB.lock(1);
        /*for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockDB.lock(1);
                }
            });
            thread.start();
        }*/
    }

}
