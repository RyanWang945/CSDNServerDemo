package com.ryan.user.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_user")
public class User implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;

    private String mobile;
    private String password;
    private String nickname;
    private String sex;
    private Date birthday;
    private String avatar;
    private String email;
    private Date regdate;
    private Date updatedate;
    private Date lastdate;
    private Long online;
    private String interest;
    private String personality;
    private Integer fanscount;
    private Integer followcount;

    public User() {
    }

    public User(String id, String mobile, String password, String nickname, String sex, Date birthday, String avatar, String email, Date regdate, Date updatedate, Date lastdate, Long online, String interest, String personality, Integer fanscount, Integer followcount) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.avatar = avatar;
        this.email = email;
        this.regdate = regdate;
        this.updatedate = updatedate;
        this.lastdate = lastdate;
        this.online = online;
        this.interest = interest;
        this.personality = personality;
        this.fanscount = fanscount;
        this.followcount = followcount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public Long getOnline() {
        return online;
    }

    public void setOnline(Long online) {
        this.online = online;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public Integer getFanscount() {
        return fanscount;
    }

    public void setFanscount(Integer fanscount) {
        this.fanscount = fanscount;
    }

    public Integer getFollowcount() {
        return followcount;
    }

    public void setFollowcount(Integer followcount) {
        this.followcount = followcount;
    }

    public String toString() {
        return "User{id = " + id + ", mobile = " + mobile + ", password = " + password + ", nickname = " + nickname + ", sex = " + sex + ", birthday = " + birthday + ", avatar = " + avatar + ", email = " + email + ", regdate = " + regdate + ", updatedate = " + updatedate + ", lastdate = " + lastdate + ", online = " + online + ", interest = " + interest + ", personality = " + personality + ", fanscount = " + fanscount + ", followcount = " + followcount + "}";
    }
}
