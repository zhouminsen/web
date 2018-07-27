package org.zjw.web.controller.sessionAndCookie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhoum on 2018/7/18.
 */
@Controller
@RequestMapping("/sessionAndCookie")
public class SessionAndCookieController {

    @ResponseBody
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request) {
        /*只有有这行代码才会创建jsessionid,并将jsessionid的值写入到浏览器的cookie中,
           这样浏览器下次访问的时候就会带上jsessionid来确定服务端和客户端之间的关系*/
        request.getSession();
        return request.getSession().getId();
    }


    @RequestMapping(value = "/notSession", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String notSession() {
        return "我不会生成jsessionid的";
    }


    //跳转到jsp页面
    @RequestMapping("/doJsp")
    public String doJsp() {
        return "/sessionAndCookie/doJsp";
    }

}
