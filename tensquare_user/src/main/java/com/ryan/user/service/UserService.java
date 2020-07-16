package com.ryan.user.service;

import com.ryan.user.dao.UserDao;
import com.ryan.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ryan
 * @date 2020/7/12 16:52
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User selectById(String userId){
        return userDao.selectById(userId);
    }
}
