package com.ryan.article.repository;

import com.ryan.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Ryan
 * @date 2020/7/11 16:03
 */
public interface CommentRepository extends MongoRepository<Comment,String> {
    //SpringDataMongoDB,支持通过查询方法名进行查询定义的方式

    //根据文章id查询文章评论数据
    List<Comment> findByArticleid(String articleId);

    //根据发布时间和点赞数查询
    //List<Comment> findByPublishdateAAndThumbup(Date data,Integer thumbup);

    //根据用户id查询,并根据发布时间倒序排序
    //List<Comment> findByUseridOrderByPublishdateDesc(String userid);

}
