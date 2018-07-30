package org.zjw.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zjw.web.other.lock.LockRedis;

/**
 * Created by zhoum on 2018/7/30.
 */

@RequestMapping("/lock")
@Controller
public class LockContoller {

    @Autowired
    private LockRedis lockRedis;

    @RequestMapping(value = "/unlock", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String unlock(String id) {
        lockRedis.unlock(id);
        return "完成";
    }

    @RequestMapping(value = "/getInfo", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getInfo(String id) {
        String info = lockRedis.getInfo(id);
        return info;
    }
}
