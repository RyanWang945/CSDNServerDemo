package com.ryan.article.service;

import com.ryan.article.pojo.Comment;
import com.ryan.article.repository.CommentRepository;
import com.ryan.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Ryan
 * @date 2020/7/11 16:05
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Comment> findAll(){
        List<Comment> list=commentRepository.findAll();
        return list;
    }

    public Comment findById(String commentId){
        Comment comment = commentRepository.findById(commentId).get();
        return  comment;
    }

    public void save(Comment comment){
        //分布式id生成器生成id
        String id=idWorker.nextId()+"";
        comment.set_id(id);
        //初始化点赞数据，发布时间等
        comment.setThumbup(0);
        comment.setPublishdate(new Date());
        //保存数据
        commentRepository.save(comment);
    }

    public void updateById(Comment comment){
        //使用的是MongoRepository的方法
        //save，主键如果存在，执行修改，如果不存在执行新增
        commentRepository.save(comment);
    }

    public void deleteById(String commentId){
        commentRepository.deleteById(commentId);
    }

    public List<Comment> findByArticleId(String articleId){
        //调用持久层，根据文章id查询，但是并没有提供，需要自己定义。
        return commentRepository.findByArticleid(articleId);
    }
    public void thumbUp(String commentId){
        //i++无法保证线程安全
        //分布式锁-redis/zookeeper来解决，只有重要数据使用这个分布式锁
        //------------------------------------------
//        //根据评论id查询评论数据
//        Comment comment = commentRepository.findById(commentId).get();
//        //对评论点赞数据加一
//        comment.setThumbup(comment.getThumbup()+1);
//        //保存修改数据
//        commentRepository.save(comment);
        //-------------------------------------------
        //点赞功能优化
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        //封装修改的数值
        Update update=new Update();
        //使用inc列值增长
        update.inc("thumbup",1);

        mongoTemplate.updateFirst(query,update,"comment");
    }
}
