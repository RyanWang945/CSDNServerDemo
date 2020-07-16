package com.ryan.client;

import com.ryan.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ryan
 * @date 2020/7/14 15:59
 */
@FeignClient("tensquare-user")
public interface UserClient {

    @RequestMapping(value = "user/{userId}",method = RequestMethod.GET)
    Result selectById(@PathVariable("userId") String userId);

}
