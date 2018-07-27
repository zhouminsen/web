package org.zjw.web.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zjw.web.dao.CalcCountDao;
import org.zjw.web.entity.CalcCount;

/**
 * Created by zhoum on 2018/7/27.
 */
@Component
public class LockDB {

    @Autowired
    private CalcCountDao calcCountDao;

    public void lock(int id) {
        int line = 0;
        while (line < 1) {
            CalcCount calcCount = calcCountDao.selectByPrimaryKey(id);
            line = calcCountDao.increment(calcCount);
        }
    }
}
