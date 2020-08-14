package com.ryan.article.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ryan
 * @date 2020/7/10 9:31
 */
@TableName("tb_article")
public class Article implements Serializable {
    @TableId(type = IdType.INPUT)//表示mybatisplus的主键策略，用户输入。
    private String id;//ID
    private String columnid; //专栏ID
    private String userid; //用户ID
    private String title; //标题
    private String content; //文章正文
    private String image; //文章封面
    private Date createtime; //发表日期
    private Date updatetime; //修改日期
    private String ispublic; //是否公开
    private String istop; //是否置顶
    private Integer visits;
    private Integer thumbup;
    private Integer comment; //评论数
    private String state; //审核状态
    private String channelid; //所属频道
    private String url; //URL
    private String type; //类型

    public Article() {
    }

    public Article(String id, String columnid, String userid, String title, String content, String image, Date createtime, Date updatetime, String ispublic, String istop, Integer visits, Integer thumbup, Integer comment, String state, String channelid, String url, String type) {
        this.id = id;
        this.columnid = columnid;
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.ispublic = ispublic;
        this.istop = istop;
        this.visits = visits;
        this.thumbup = thumbup;
        this.comment = comment;
        this.state = state;
        this.channelid = channelid;
        this.url = url;
        this.type = type;
    }

    /**
     * 获取
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     * @return columnid
     */
    public String getColumnid() {
        return columnid;
    }

    /**
     * 设置
     * @param columnid
     */
    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    /**
     * 获取
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取
     * @return updatetime
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置
     * @param updatetime
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取
     * @return ispublic
     */
    public String getIspublic() {
        return ispublic;
    }

    /**
     * 设置
     * @param ispublic
     */
    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }

    /**
     * 获取
     * @return istop
     */
    public String getIstop() {
        return istop;
    }

    /**
     * 设置
     * @param istop
     */
    public void setIstop(String istop) {
        this.istop = istop;
    }

    /**
     * 获取
     * @return visits
     */
    public Integer getVisits() {
        return visits;
    }

    /**
     * 设置
     * @param visits
     */
    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    /**
     * 获取
     * @return thumbup
     */
    public Integer getThumbup() {
        return thumbup;
    }

    /**
     * 设置
     * @param thumbup
     */
    public void setThumbup(Integer thumbup) {
        this.thumbup = thumbup;
    }

    /**
     * 获取
     * @return comment
     */
    public Integer getComment() {
        return comment;
    }

    /**
     * 设置
     * @param comment
     */
    public void setComment(Integer comment) {
        this.comment = comment;
    }

    /**
     * 获取
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取
     * @return channelid
     */
    public String getChannelid() {
        return channelid;
    }

    /**
     * 设置
     * @param channelid
     */
    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    /**
     * 获取
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 设置
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "Article{id = " + id + ", columnid = " + columnid + ", userid = " + userid + ", title = " + title + ", content = " + content + ", image = " + image + ", createtime = " + createtime + ", updatetime = " + updatetime + ", ispublic = " + ispublic + ", istop = " + istop + ", visits = " + visits + ", thumbup = " + thumbup + ", comment = " + comment + ", state = " + state + ", channelid = " + channelid + ", url = " + url + ", type = " + type + "}";
    }
}