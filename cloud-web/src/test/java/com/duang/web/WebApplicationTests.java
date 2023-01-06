package com.duang.web;

import com.duang.support.support.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(value = "dev")
@SpringBootTest(classes = com.duang.web.WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAspectJAutoProxy(exposeProxy = true)
public class WebApplicationTests {

    @Resource
    private RedisUtils redisUtils;
    @Test
   public void contextLoads() {
        System.out.println(redisUtils.get("a"));
    }

}
