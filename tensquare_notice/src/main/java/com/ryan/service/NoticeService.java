package com.ryan.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ryan.client.ArticleClient;
import com.ryan.client.UserClient;
import com.ryan.dao.NoticeDao;
import com.ryan.dao.NoticeFreshDao;
import com.ryan.entity.Result;
import com.ryan.pojo.Notice;
import com.ryan.pojo.NoticeFresh;
import com.ryan.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ryan
 * @date 2020/7/14 11:04
 */
@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private NoticeFreshDao noticeFreshDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleClient articleClient;

    @Autowired
    private UserClient userClient;

    //完善消息内容
    private void getInfo(Notice notice){
        //查询用户昵称
        Result userResult = userClient.selectById(notice.getOperatorId());
        HashMap userMap = (HashMap) userResult.getData();
        notice.setOperatorName(userMap.get("nickname").toString());
        //查询文章名称
        Result articleResult = articleClient.findById(notice.getTargetId());
        HashMap articleMap=(HashMap) articleResult.getData();
        //将消息名称设置进去
        notice.setTargetName(articleMap.get("title").toString());
    }

    public Notice selectById(String id){
        Notice notice = noticeDao.selectById(id);
        getInfo(notice);
        return notice;
    }


    public Page<Notice> selectByPage(Notice notice, Integer page, Integer size) {
        //封装分页对象
        Page<Notice> pageData=new Page<>(page,size);
        List<Notice> notices = noticeDao.selectPage(pageData, new EntityWrapper<>(notice));
        //设置结果集到分页对象中
        for(Notice n:notices){
            getInfo(n);
        }
        pageData.setRecords(notices);
        return pageData;
    }

    public void save(Notice notice) {
        //设置初始值
        //设置状态，0表示未读消息，1表示已读消息
        notice.setState("0");
        notice.setCreatetime(new Date());
        //使用分布式id生成器生成id
        String id=idWorker.nextId()+"";
        notice.setId(id);
        noticeDao.insert(notice);

        //新的待推送消息入库
//        NoticeFresh noticeFresh=new NoticeFresh();
//        noticeFresh.setNoticeId(id);
//        noticeFresh.setUserId(notice.getReceiverId());
//        noticeFreshDao.insert(noticeFresh);
    }

    public void updateById(Notice notice) {
        noticeDao.updateById(notice);
    }

    public Page<NoticeFresh> freshPage(String userId, Integer page, Integer size) {
        //封装查询条件
        NoticeFresh noticeFresh=new NoticeFresh();
        noticeFresh.setUserId(userId);
        //创建分页
        Page<NoticeFresh> pageData=new Page<>(page,size);
        //执行查询
        List<NoticeFresh> noticeFreshes = noticeFreshDao.selectPage(pageData, new EntityWrapper<>(noticeFresh));

        //设置查询结果集到分页对象中
        pageData.setRecords(noticeFreshes);
        //返回结果
        return pageData;
    }

    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshDao.delete(new EntityWrapper<>(noticeFresh));
    }
}
