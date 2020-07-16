package com.ryan.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ryan.article.client.NoticeClient;
import com.ryan.article.dao.ArticleDao;
import com.ryan.article.pojo.Article;
import com.ryan.article.pojo.Notice;
import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import com.ryan.util.IdWorker;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Ryan
 * @date 2020/7/10 9:59
 */
@Service
public class ArticleService {

    @Autowired(required = false)
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private NoticeClient noticeClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;


    public List<Article> findAll(){
        return articleDao.selectList(null);
    }
    public Article findById(String articleId){
        return articleDao.selectById(articleId);
    }
    public void save(Article article){
        //TODO:使用jwt鉴权获取当前用户的信息，用户id，也就是文章作者id
        String userId="3";
        article.setUserid(userId);
        String id=idWorker.nextId()+"";
        article.setId(id);
        //初始化数据
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);

        //新增
        articleDao.insert(article);
        //新增文章后创建消息，通知给订阅者



        //获取订阅者信息
        //存放作者订阅信息的集合key，里面存放订阅者id
        String authorKey="article_author_"+article.getUserid();
        Set<String> set = redisTemplate.boundSetOps(authorKey).members();

        //给订阅者创建消息通知
        for(String uid:set){
            //创建消息对象
            Notice notice=new Notice();
            notice.setReceiverId(uid);
            notice.setOperatorId(userId);
            notice.setAction("publish");
            notice.setTargetType("article");
            notice.setTargetId(id);
            notice.setType("sys");
            noticeClient.save(notice);
        }
        //发消息给rabbitmq，就是新消息的通知
        //第一个参数，交换机名，使用之前完成的订阅功能的交换机
        //第二个参数是路由键，使用文章作者的id作为路由键
        //第三个参数是消息内容，这里只完成新消息的提醒，内容是文章id
        rabbitTemplate.convertAndSend("article_subscribe",userId,id);
    }
    public void updateById(Article article){
        //根据主键id修改
        articleDao.updateById(article);
        //根据条件修改
        //创建条件对象
//        EntityWrapper<Article> wrapper=new EntityWrapper<>();
//        //设置条件
//        wrapper.eq("id",article.getId());
//        articleDao.update(article,wrapper);
    }
    public void deleteById(String articleId){
        articleDao.deleteById(articleId);
    }
    public Page<Article> findByPage(Map<String,Object> map, Integer page,Integer size){
        //设置查询条件
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for(String key:keySet){
//            if(map.get(key)!=null){
//                wrapper.eq(key,map.get(key));
//            }
            //第一个参数是个布尔，是否吧后面的条件加入到查询条件中，和上面的if判断是一个效果
            wrapper.eq(map.get(key)!=null,key,map.get(key));
        }
        //设置分页参数
        Page<Article> pageData=new Page<>(page,size);
        //执行查询
        //第一个是分页参数，第二个是查询条件
        List<Article> list = articleDao.selectPage(pageData, wrapper);
        pageData.setRecords(list);

        //返回
        return pageData;
    }

    public Boolean subscribe(String articleId, String userId) {
        //根据文章id查询文章作者id
        String authorId = articleDao.selectById(articleId).getUserid();

        //创建一个rabbitmq的管理器，通过管理器创建各种东西
        RabbitAdmin rabbitAdmin=new RabbitAdmin(rabbitTemplate.getConnectionFactory());

        //声明交换机，用来处理新增文章消息
        DirectExchange exchange=new DirectExchange("article_subscribe");
        rabbitAdmin.declareExchange(exchange);

        //声明队列，每个用户都有自己打的队列，通过用户id区分
        Queue queue=new Queue("article_subscribe_"+userId,true);

        //声明交换机和队列的绑定关系，确保队列只收到对应作者的新增文章消息，通过路由键绑定
        //第一个是队列，第二个是交换机，第三个是路由键
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(authorId);

        //比如我们绑定了一个鲁迅的路由键到一个队列中，那么这个队列使能收到鲁迅的新增文章
        //通过路由键进行绑定作者，队列只收到绑定作者的文章消息


        //存放用户订阅信息的集合key，里面存放作者id
        String userKey="article_subscribe_"+userId;
        //存放作者订阅者的信息的集合，里面存放订阅者id
        String authorKey="article_author_"+authorId;
        //查询用户的订阅关系是否有订阅该作者，如果没，就订阅，如果有，就取消
        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(authorId);
        if(flag){
            //如果订阅了作者，就取消订阅
            //双向取消
            redisTemplate.boundSetOps(userKey).remove(authorId);
            redisTemplate.boundSetOps(authorKey).remove(userId);

            //如果取消订阅，那么就要删除队列绑定关系、
            rabbitAdmin.removeBinding(binding);
            return false;
        }else{
            redisTemplate.boundSetOps(userKey).add(authorId);
            redisTemplate.boundSetOps(authorKey).add(userId);

            //如果订阅，那么就要声明绑定的队列
            rabbitAdmin.declareQueue(queue);
            //添加绑定关系
            rabbitAdmin.declareBinding(binding);
            return true;
        }
        
    }
    public void thumbup(String articleId,String userId){
        Article article=articleDao.selectById(articleId);
        article.setThumbup(article.getThumbup()+1);
        articleDao.updateById(article);
        //点赞成功后，需要发送消息给文章作者
        Notice notice=new Notice();
        notice.setReceiverId(article.getUserid());
        notice.setOperatorId(userId);
        notice.setAction("publish");
        notice.setTargetType("article");
        notice.setTargetId(articleId);
        notice.setType("user");

        noticeClient.save(notice);

        RabbitAdmin rabbitAdmin=new RabbitAdmin(rabbitTemplate.getConnectionFactory());



        //声明队列，每个用户都有自己打的队列，通过用户id区分
        Queue queue=new Queue("article_thumbup_"+article.getUserid(),true);

        //发消息到队列中
        rabbitTemplate.convertAndSend("article_thumbup_"+article.getUserid(),articleId);

    }
}
