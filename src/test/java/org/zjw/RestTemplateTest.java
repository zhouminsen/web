package org.zjw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhoum on 2019-08-23.
 */
public class RestTemplateTest extends BaseTest {
    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void get() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://127.0.0.1:8081/web/params/submit5", String.class);
        System.out.println("是什么：" + forEntity.getBody());
    }
}
