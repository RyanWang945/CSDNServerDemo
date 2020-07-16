package com.ryan.article.controller;

import com.ryan.article.pojo.Comment;
import com.ryan.article.service.CommentService;
import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ryan
 * @date 2020/7/11 16:05
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;
    //Get /comment 查询所有评论
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Comment> list=commentService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    //Get /comment/{commentId}根据评论id查询数据
    @RequestMapping(value="{commentId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String commentId){
        Comment comment=commentService.findById(commentId);
        return new Result(true,StatusCode.OK,"查询成功",comment);
    }
    //Post /comment 新增评论
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true,StatusCode.OK,"新增成功");
    }

    //put /comment/{commentId}
    @RequestMapping(value="{commentId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String commentId,@RequestBody Comment comment){
        //设置评论主键
        comment.set_id(commentId);
        commentService.updateById(comment);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //delete /comment/{commentId}
    @RequestMapping(value="{commentId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String commentId){
        commentService.deleteById(commentId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    //get /comment/article/{articleId} 根据文章id查询文章评论
    @RequestMapping(value="article/{articleId}",method = RequestMethod.GET)
    public Result findByArticleId(@PathVariable String articleId){
        List<Comment> list=commentService.findByArticleId(articleId);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }
    //put /comment/thumbup/{commentId}根据评论id点赞评论
    @RequestMapping(value="thumbup/{commentId}",method=RequestMethod.PUT)
    public Result thumbUp(@PathVariable String commentId){
        //把用户点赞信息保存到redis中
        //每次点赞之前先查询用户点赞信息，如果没有就可以点赞，如果有就不能重复点赞
        //查询用户点赞信息，根据用户id和评论id
        //模拟用户id
        String userId="123";
        Object flag = redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);
        //判断查询到的结果是否为空，空就是没有点赞，可以点赞
        if(flag==null){
            commentService.thumbUp(commentId);
            redisTemplate.opsForValue().set("thumbup_" + userId + "_" + commentId,1);
            return new Result(true,StatusCode.OK,"点赞成功");
        }else{
            return new Result(false,StatusCode.REPERROR,"不能重复点赞");
        }

        //如果不为空，表示用户点过赞



    }

}
