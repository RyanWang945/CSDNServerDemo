package com.ryan.article.controller;

import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ryan
 * @date 2020/7/10 14:20
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class)//具体拦截什么异常
    @ResponseBody
    public Result handler(Exception e){
        System.out.println("处理异常");
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
