package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.web.service.CalcCountService;

/**
 * Created by zhoum on 2019-05-30.
 */
public class TransactionTest extends BaseTest {

    @Autowired
    private CalcCountService calcCountService;

    @Test
    public void test() {
        calcCountService.transaction();
    }

    @Test
    public void test2() throws Exception {
        calcCountService.transaction2();
    }
}
