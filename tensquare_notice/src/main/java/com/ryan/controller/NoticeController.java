package com.ryan.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ryan.entity.PageResult;
import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import com.ryan.pojo.Notice;
import com.ryan.pojo.NoticeFresh;
import com.ryan.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ryan
 * @date 2020/7/14 11:05
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    //根据id查询消息通知，127.0.0.1:9014/notice/{id}  GET
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Result selectById(@PathVariable String id){
        Notice notice=noticeService.selectById(id);
        return new Result(true, StatusCode.OK,"查询成功",notice);
    }

    //根据条件分页查询消息通知 127.0.0.1:9014/notice/search/{page}/{size} post
    @RequestMapping(value = "search/{page}/{size}",method = RequestMethod.POST)
    public Result selectByList(@RequestBody Notice notice,
                               @PathVariable Integer page,
                               @PathVariable Integer size){
        Page<Notice> pageData=noticeService.selectByPage(notice,page,size);
        PageResult<Notice> pageResult=new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    //新增通知 http://127.0.0.1:9014/notice Post
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Notice notice){
        noticeService.save(notice);
        return new Result(true,StatusCode.OK,"新增成功");
    }

    //修改通知 http://127.0.0.1:9014/notice PUT
    @RequestMapping(method = RequestMethod.PUT)
    public Result updateById(@RequestBody Notice notice){
        noticeService.updateById(notice);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    //根据用户id查询该用户的待推送消息（新消息）
    // http://127.0.0.1:9014/notice/fresh/{userId}/{page}/{size} PUT
    @RequestMapping(value="fresh/{userId}/{page}/{size}",method=RequestMethod.GET)
    public Result freshPage(@PathVariable String userId,
                            @PathVariable Integer page,
                            @PathVariable Integer size){
        Page<NoticeFresh> pageData=noticeService.freshPage(userId,page,size);
        PageResult<NoticeFresh> pageResult=new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    //删除待推送消息（新消息）
    //http://127.0.0.1:9014/notice/fresh DELETE
    @RequestMapping(value="fresh",method = RequestMethod.DELETE)
    public Result freshDelete(@RequestBody NoticeFresh noticeFresh){
        noticeService.freshDelete(noticeFresh);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
