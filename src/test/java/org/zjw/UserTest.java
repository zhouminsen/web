package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.web.dao.UserDao;
import org.zjw.web.entity.User;

import java.util.Date;

/**
 * Created by zhoum on 2019-07-23.
 */
public class UserTest extends BaseTest {
    @Autowired
    UserDao userDao;

    @Test
    public void insert() {
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setName("周家伟" + i);
            user.setAge(i);
            user.setBirthday(new Date());
            user.setAddress("湖北" + i);
            user.setIdentityCard(i);
            userDao.insert(user);
        }
    }

}
