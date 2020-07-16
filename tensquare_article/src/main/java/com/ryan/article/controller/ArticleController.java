package com.ryan.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ryan.article.pojo.Article;
import com.ryan.article.service.ArticleService;
import com.ryan.entity.PageResult;
import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @date 2020/7/10 9:59
 */
@RestController
@RequestMapping("/article")
@CrossOrigin//跨域处理注解，现在是支持所有跨域
//如果我们想只支持单一跨域该怎么办
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate redisTemplate;


    //测试公共异常处理
    @RequestMapping(value = "exception",method = RequestMethod.GET)
    public Result test(){
        int a=1/0;
        return null;
    }

    //Get  /article 文章全部列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Article> list=articleService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    @RequestMapping(value="{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId){
        Article article=articleService.findById(articleId);
        return new Result(true,StatusCode.OK,"查询成功",article);
    }
    @RequestMapping(method=RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true,StatusCode.OK,"新增成功");
    }
    @RequestMapping(value = "{articleId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String articleId,
                             @RequestBody Article article){
        article.setId(articleId);
        articleService.updateById(article);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    @RequestMapping(value = "{articleId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId){
        articleService.deleteById(articleId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    //之前接收文章数据使用pojo，但是现在是根据条件查询
    //而所有条件都需要进行判断，遍历pojo所有属性需要使用反射，性能低
    //所以采用集合
    @RequestMapping(value="search/{page}/{size}",method = RequestMethod.POST)
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer size,
                             @RequestBody Map<String,Object> map){
        Page<Article> pageData=articleService.findByPage(map,page,size);
        PageResult<Article> pageResult=new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    //根据文章作者id和用户id，建立订阅关系
    //http://127.0.0.1:9004/article/subscribe POST
    @RequestMapping(value="subscribe",method = RequestMethod.POST)
    public Result subscribe(@RequestBody Map map){
        //如果返回true，就是订阅该作者，如果返回false 就是取消订阅
        Boolean flag=articleService.subscribe(map.get("articleId").toString(),
                map.get("userId").toString());
        if(flag){
            return new Result(true,StatusCode.OK, "订阅成功");
        }else{
            return new Result(true,StatusCode.OK,"取消订阅成功");
        }
    }
    //根据文章id点赞文章
    //http://127.0.0.1:9004/article/thumbup/{articleId} PUT
    @RequestMapping(value = "thumbup/{articleId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String articleId) {
        //TODO 使用JWT鉴权的方式获取当前用户的id
        String userId = "1";

        //查询用户对文章的点赞信息，根据用户id和文章id
        String key = "thumbup_article_" + userId + "_" + articleId;
        Object flag = redisTemplate.opsForValue().get(key);

        //判断查询到的结果是否为空
        if (flag == null) {
            //如果为空，表示用户没有点过赞，就可以进行点赞操作
            articleService.thumbup(articleId,userId);
            //点赞成功，保存点赞信息
            redisTemplate.opsForValue().set(key, 1);

            return new Result(true, StatusCode.OK, "点赞成功");

        } else {
            //如果不为空，表示用户已经点过赞，不可以重复点赞
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }
    }
}
