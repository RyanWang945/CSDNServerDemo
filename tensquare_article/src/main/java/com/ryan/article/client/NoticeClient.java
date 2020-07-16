package com.ryan.article.client;


import com.ryan.article.pojo.Notice;
import com.ryan.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ryan
 * @date 2020/7/14 18:37
 */
@FeignClient("tensquare-notice")
public interface NoticeClient {
    //新增通知 http://127.0.0.1:9014/notice Post
    @RequestMapping(value="notice",method = RequestMethod.POST)
    public Result save(@RequestBody Notice notice);
}
