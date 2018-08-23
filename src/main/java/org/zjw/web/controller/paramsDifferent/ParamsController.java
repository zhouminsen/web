package org.zjw.web.controller.paramsDifferent;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by zhoum on 2018/8/23.
 */
@Controller
@RequestMapping("/params")
public class ParamsController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/doForm")
    public String doForm(HttpServletRequest request) {

        return "/paramsDifferent/form";
    }


    @RequestMapping(value = "/submit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User submit(User user) {

        return user;
    }

    @RequestMapping(value = "/submit2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User submit2(@RequestBody User user) {

        return user;
    }


    @RequestMapping(value = "/submit3", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String submit3(@RequestBody String user) {

        return user;
    }


    @Data
    private static class User {
        private String username;

        private Integer age;

    }

}



