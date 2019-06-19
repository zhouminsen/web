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
public class CalcCountService {

    @Autowired
    private CalcCountDao calcCountDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    public void transaction() {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    CalcCount calcCount = new CalcCount();
                    calcCount.setName("伟哥来了");
                    calcCount.setStoreCount(1);
                    calcCount.setVersion(1);
                    calcCountDao.insertSelective(calcCount);
                    calcCount.setName("伟哥来了2");
                    calcCount.setStoreCount(2);
                    calcCount.setVersion(2);
                    calcCountDao.insertSelective(calcCount);
//                    int i = 1 / 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                }
                return null;
            }
        });

    }

    public void transaction2() throws Exception {
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
        try {
            CalcCount calcCount = new CalcCount();
            calcCount.setName("伟哥来了");
            calcCount.setStoreCount(1);
            calcCount.setVersion(1);
            calcCountDao.insertSelective(calcCount);
            calcCount.setName("伟哥来了2");
            calcCount.setStoreCount(2);
            calcCount.setVersion(2);
            calcCountDao.insertSelective(calcCount);
            int i = 1 / 0;
            transactionManager.commit(transStatus);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(transStatus);
            throw new Exception(e.toString(), e);
        }
    }

    public void transaction3() throws Exception {
        transaction4();

    }

    @Transactional
    public void transaction4() throws Exception {
        CalcCount calcCount = new CalcCount();
        calcCount.setName("伟哥来了");
        calcCount.setStoreCount(1);
        calcCount.setVersion(1);
        calcCountDao.insertSelective(calcCount);
        int i = 1 / 0;
        calcCount.setName("伟哥来了2");
        calcCount.setStoreCount(2);
        calcCount.setVersion(2);
        calcCountDao.insertSelective(calcCount);
    }
}
