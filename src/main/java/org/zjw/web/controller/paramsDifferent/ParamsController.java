package org.zjw.web.controller.paramsDifferent;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zjw.web.entity.User2;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoum on 2018/8/23.
 */
@Controller
@RequestMapping("/params")
public class ParamsController {


    @RequestMapping("/doForm")
    public String doForm(HttpServletRequest request) {

        return "/paramsDifferent/form";
    }


    @RequestMapping(value = "/submit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User2 submit(User2 user) {

        return user;
    }

    @RequestMapping(value = "/submit2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User2 submit2(@RequestBody User2 user) {

        return user;
    }


    @RequestMapping(value = "/submit3", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String submit3(@RequestBody String user) {

        return user;
    }

    @RequestMapping(value = "/submit4")
    @ResponseBody
    public parent submit4(parent parent) {

        return parent;
    }


    @RequestMapping(value = "/submit5")
    @ResponseBody
    public String submit5() {

        return "hahahaha";
    }

    @Data
    private static class parent {
        private List<User2> userList = new ArrayList<>();

        private User2 user;

    }

}



