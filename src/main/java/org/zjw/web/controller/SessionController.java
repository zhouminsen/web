package org.zjw.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoum on 2018/7/18.
 */
@Controller
@RequestMapping("/session")
public class SessionController {

    @ResponseBody
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request) {
        /*只有有这行代码才会创建jsessionid,并将jsessionid的值写入到浏览器的cookie中,
           这样浏览器下次访问的时候就会带上jsessionid来确定服务端和客户端之间的关系*/
        request.getSession().isNew();
        return request.getSession().getId();
    }

    /**
     * 禁用cookie
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSession2", produces = "text/html;charset=utf-8")
    public String getSession2(HttpServletRequest request, HttpServletResponse response) {
        /*只有有这行代码才会创建jsessionid,并将jsessionid的值写入到浏览器的cookie中,
           这样浏览器下次访问的时候就会带上jsessionid来确定服务端和客户端之间的关系*/
        request.getSession().isNew();
        //浏览器禁用cookie，必须显示的调用该行代码重写url，这样可以让服务端识别出是来自同个客户端的请求，否则无法识别
        String s = response.encodeRedirectURL(request.getContextPath() + "/session/getSession2");
        return "<a href='" + s + "'>点击我，看session是否是一样的</a>";
    }


    @RequestMapping(value = "/notSession", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String notSession() {
        return "我不会生成jsessionid的";
    }


    //跳转到jsp页面
    @RequestMapping("/doJsp")
    public String doJsp() {
        return "/sessionJsp";
    }

}
