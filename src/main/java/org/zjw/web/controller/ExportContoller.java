package org.zjw.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zjw.web.entity.User;
import org.zjw.web.other.lock.LockRedis;
import org.zjw.web.util.POIExcelExport;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoum on 2018/7/30.
 */

@RequestMapping("/export")
@Controller
public class ExportContoller {


    @RequestMapping(value = "download")
    @ResponseBody
    public String exprot(HttpServletResponse response) throws IOException {
        List<User> resultList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(0);
            user.setName("账户" + i);
            user.setAddress("湖北" + i);
            user.setAge(i);
            user.setIdentityCard(i);
            resultList.add(user);
        }
        String fileName = "账户列表";
        POIExcelExport.exportFinAccount(resultList, fileName, response);
        return "完成";
    }

}
