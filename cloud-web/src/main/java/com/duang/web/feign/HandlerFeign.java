package com.duang.web.feign;

import com.duang.cloudcommons.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("cloud-handler")
@RequestMapping("/queue")
public interface HandlerFeign {

    @RequestMapping("/get")
    @ResponseBody
    User get(@RequestParam("id") Long id);

}
