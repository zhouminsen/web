package org.zjw.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.zjw.web.dao.CalcCountDao;
import org.zjw.web.entity.CalcCount;

/**
 * Created by zhoum on 2019-05-30.
 */
@Service
public class CalcService {

    @Autowired
    private CalcCountDao calcCountDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private CalcCountService calcCountService;

    public void transaction() {

        try {
            calcCountService.transaction4();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
