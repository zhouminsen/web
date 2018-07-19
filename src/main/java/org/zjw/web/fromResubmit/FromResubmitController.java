package org.zjw.web.fromResubmit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String submit(HttpServletRequest request) {
        //取出客户端表单中的token,如果表单中没有token则认为用户是重复提交
        String clientToken = request.getParameter("token");
        if (StringUtils.isEmpty(clientToken)) {
            return "请不要重复提交";
        }
        //取出服务端当前用户session中的token,如果为空则认为用户重复提交
        String serviceToken = (String) request.getSession().getAttribute("token");
        if (StringUtils.isEmpty(serviceToken)) {
            return "请不要重复提交";
        }
        //判断两个客户端和服务端我token是否相同,不相同认为用户重复提交
        if (!Objects.equals(clientToken, serviceToken)) {
            return "请不要重复提交";
        }

        //确定本次请求不是重复提交,移除session中的token
        request.getSession().removeAttribute("token");
        return "提交成功";
    }
}