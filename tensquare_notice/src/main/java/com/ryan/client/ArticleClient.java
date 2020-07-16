package com.ryan.client;

import com.ryan.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ryan
 * @date 2020/7/14 15:56
 */
@FeignClient("tensquare-article")
public interface ArticleClient {

    //根据文章id查询文章数据
    @RequestMapping(value="article/{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("articleId") String articleId);
}
