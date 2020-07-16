package com.ryan.user.controller;

import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import com.ryan.user.pojo.User;
import com.ryan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan
 * @date 2020/7/12 16:53
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //根据id查询用户
    //9008/user/1
    @RequestMapping(value = "{userId}",method = RequestMethod.GET)
    public Result selectById(@PathVariable String userId){
        User user=userService.selectById(userId);
        return new Result(true, StatusCode.OK,"查询成功",user);
    }

}
