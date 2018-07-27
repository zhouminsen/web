package org.zjw.web.entity;

import lombok.Data;

@Data
public class CalcCount {
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