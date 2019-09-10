package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.zjw.web.service.UserService;

/**
 * Created by Administrator on 2019-08-06.
 */
public class UserTest extends BaseTest {

    @Autowired
    private UserService userService;



    @Test
    public void test() {
        userService.bb();
    }
}
