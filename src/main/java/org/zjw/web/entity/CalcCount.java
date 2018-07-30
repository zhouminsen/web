package org.zjw.web.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalcCount implements Serializable {


    private static final long serialVersionUID = -8098570595898776365L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 库存
     */
    private Integer storeCount;

    /**
     * 名称
     */
    private String name;

    /**
     * 版本号
     */
    private Integer version;

}