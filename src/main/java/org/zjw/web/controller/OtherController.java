package org.zjw.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zjw.web.service.CalcService;
import org.zjw.web.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhoum on 2018/7/26.
 */
@Controller
@RequestMapping("/other")
public class OtherController {

    @Autowired
    private UserService userService;

    /**
     * 获取终端
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTerminal", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getTerminal(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("Android".toLowerCase())) {
            return "Android终端访问";
        } else if (userAgent.contains("iPhone".toLowerCase())) {
            return "IOS终端访问";
        } else if (userAgent.contains("Window   s".toLowerCase())) {
            return "Windows终端访问";
        }
        return "未知终端";
    }


    /**
     * 测试对象代理
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/testProxyObject", produces = "text/html;charset=UTF-8")
    public void testProxyObject(HttpServletRequest request) {
        System.out.println(userService);
    }

}
