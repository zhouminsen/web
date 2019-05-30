package org.zjw.web.util;


import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by zhoum on 2019-05-23.
 */
public class LockModel implements Serializable {


    private static final long serialVersionUID = -7823546828111322529L;

    private HashSet<String> lockModels = new HashSet<>();
    /**
     * true：成功   false：失败
     */
    private Boolean flag;

    public HashSet<String> getLockModels() {
        return lockModels;
    }

    public void setLockModels(HashSet<String> lockModels) {
        this.lockModels = lockModels;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean success) {
        flag = success;
    }
}
