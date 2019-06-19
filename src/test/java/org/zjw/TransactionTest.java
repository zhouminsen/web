package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.web.service.CalcCountService;
import org.zjw.web.service.CalcService;

/**
 * Created by zhoum on 2019-05-30.
 */
public class TransactionTest extends BaseTest {

    @Autowired
    private CalcCountService calcCountService;

    @Autowired
    private CalcService calcService;

    @Test
    public void test() {
        calcCountService.transaction();
    }

    @Test
    public void test2() throws Exception {
        calcCountService.transaction2();
    }

    @Test
    public void test3() throws Exception {
        calcCountService.transaction3();
    }

    @Test
    public void test4() throws Exception {
        calcService.transaction();
    }
}
