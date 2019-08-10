package org.zjw.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zjw.web.annotation.Log;
import org.zjw.web.dao.CalcCountDao;

/**
 * Created by zhoum on 2019-05-30.
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private CalcCountDao calcCountDao;

    @Override
//    @Transactional
    @Log
    public void bb() {
        System.out.println("hahah");
    }
}
