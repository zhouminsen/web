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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}