package org.zjw.web.dao;


import org.springframework.stereotype.Repository;
import org.zjw.web.entity.CalcCount;


public interface CalcCountDao {
    /**
     * @mbg.generated 2018-07-27
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * @mbg.generated 2018-07-27
     */
    int insertSelective(CalcCount record);

    /**
     * @mbg.generated 2018-07-27
     */
    CalcCount selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated 2018-07-27
     */
    int updateByPrimaryKeySelective(CalcCount record);

    /**
     * 递增
     */
    int increment(CalcCount calcCount);
}