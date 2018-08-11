package org.zjw.web.controller.fromResubmit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by zhoum on 2018/7/19.
 */
@Controller
@RequestMapping("/fromResubmit")
public class FromResubmitController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * session验证重复提交
     *
     * @param request
     * @return
     */
    @RequestMapping("/doForm")
    public String doForm(HttpServletRequest request) {
        //创建token
        String token = UUID.randomUUID().toString();
        //在服务端session中保存token
        request.getSession().setAttribute("token", token);
        System.out.println("本次生成的token:" + token);
        //跳转到form页面
        return "/fromResubmit/form";
    }


    @RequestMapping(value = "/submit", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String submit(HttpServletRequest request) {
        //取出客户端表单中的token,如果表单中没有token则认为用户是重复提交
        String clientToken = request.getParameter("token");
        if (StringUtils.isEmpty(clientToken)) {
            return "请不要重复提交";
        }
        //取出服务端当前用户session中的token,如果为空则认为用户重复提交aaa
        String serviceToken = (String) request.getSession().getAttribute("token");
        if (StringUtils.isEmpty(serviceToken)) {
            return "请不要重复提交";
        }
        //判断客户端和服务端token是否相同,不相同认为用户重复提交
        if (!Objects.equals(clientToken, serviceToken)) {
            return "请不要重复提交";
        }

        //确定本次请求不是重复提交,移除session中的token
        request.getSession().removeAttribute("token");

        //处理逻辑
        return "提交成功";
    }


    @RequestMapping("/doForm2")
    public String doForm2(HttpServletRequest request) {
        //跳转到form页面
        return "/fromResubmit/form2";
    }


    @RequestMapping(value = "/submit2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String submit2(HttpServletRequest request) throws InterruptedException {
        System.out.println(request.getSession().getId() + ":提交成功");
        Thread.sleep(3000);
        return "提交成功";
    }


    /**
     * redis验证重复提交
     *
     * @param request
     * @return
     */
    @RequestMapping("/doForm3")
    public String doForm3(HttpServletRequest request) {
        //创建token
        String token = UUID.randomUUID().toString();
        //在redis中保存token
        stringRedisTemplate.opsForValue().set(token, "0");
        request.setAttribute("token", token);
        System.out.println("本次生成的token:" + token);
        //跳转到form页面
        return "/fromResubmit/form3";
    }


    @RequestMapping(value = "/submit3", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String submit3(HttpServletRequest request) throws InterruptedException {
        //取出客户端表单中的token,如果表单中没有token则认为用户是重复提交
        String clientToken = request.getParameter("token");
        System.out.println(clientToken);
        if (StringUtils.isEmpty(clientToken)) {
            return "请不要重复提交";
        }
        //从redis中取出token,如果为空则认为用户重复提交
        String serviceToken = stringRedisTemplate.opsForValue().get(clientToken);
        if (StringUtils.isEmpty(serviceToken)) {
            return "请不要重复提交";
        }
        //避免网路延迟,自动重复机制.将该token对应的值加1,如果下次请求过来该值!=1说明重复请求
        Long increment = stringRedisTemplate.boundValueOps(clientToken).increment(1);
        System.out.println(increment);
        if (increment != 1) {
            //利用increment 原子性 判断是否 该token 是否使用
            return "状态不一致请不要重复提交";
        }
        //确定本次请求不是重复提交,移除redis中的token
        Thread.sleep(5000);
        stringRedisTemplate.delete(clientToken);
        System.out.println("来了2");

        //处理逻辑
        return "提交成功";
    }

}
