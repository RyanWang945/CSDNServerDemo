package test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 * @date 2020/7/11 10:38
 */
public class MongoTest {
    //抽取逻辑
    private MongoClient mongoClient;
    private MongoCollection<Document> comment;
    @Before
    public void init(){
        //1 创建操作mongodb的客户端
        mongoClient=new MongoClient("192.168.195.139",27017);
        //2 选择数据库 use commentdb
        MongoDatabase commentdb = mongoClient.getDatabase("commentdb");
        //3 选择集合
        comment = commentdb.getCollection("comment");
    }
    @After
    public void after(){
        mongoClient.close();
    }
    //查询所有数据
    @Test
    public void test1(){
        //1 创建操作mongodb的客户端
        //mongoClient=new MongoClient("192.168.195.139",27017);
        //2 选择数据库 use commentdb
        //MongoDatabase commentdb = mongoClient.getDatabase("commentdb");
        //3 选择集合
        //comment = commentdb.getCollection("comment");
        //4 使用集合进行查询
        FindIterable<Document> documents = comment.find();
        //5 解析结果集
        for(Document document:documents){
            System.out.println("---------------------");
            System.out.println("_id:"+document.get("_id"));
            System.out.println("content:"+document.get("content"));
            System.out.println("userid:"+document.get("userid"));
            System.out.println("thumbup:"+document.get("thumbup"));
        }
        //释放资源,关闭客户端
        mongoClient.close();
    }
    //根据条件_id查询数据
    @Test
    public void test2(){
        //封装查询条件
        BasicDBObject bson=new BasicDBObject("_id","1");
        FindIterable<Document> documents = comment.find(bson);
        for(Document document:documents){
            System.out.println("---------------------");
            System.out.println("_id:"+document.get("_id"));
            System.out.println("content:"+document.get("content"));
            System.out.println("userid:"+document.get("userid"));
            System.out.println("thumbup:"+document.get("thumbup"));
        }
    }
    //新增
    @Test
    public void test3(){
        Map<String,Object> map=new HashMap<>();
        map.put("_id","6");
        map.put("content","新增测试");
        map.put("userid","1019");
        map.put("thumbup","666");
        Document document=new Document(map);
        comment.insertOne(document);
    }
    //修改
    @Test
    public void test4(){
        BasicDBObject filter=new BasicDBObject("_id","6");
        BasicDBObject update=new BasicDBObject("$set",new Document("userid","999"));
        //执行修改
        comment.updateOne(filter,update);
    }
    //删除
    @Test
    public void test5(){
        //创建删除的条件
        BasicDBObject bson=new BasicDBObject("_id","6");
        comment.deleteOne(bson);
    }

}
