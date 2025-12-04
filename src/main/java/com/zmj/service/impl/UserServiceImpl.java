package com.zmj.service.impl;

import com.zmj.dao.UserDao;
import com.zmj.domain.User;
import com.zmj.service.UserService;
import com.zmj.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByUsername(String username) {
        User user=userDao.findByUsername(username);
        return user;
    }

    @Override
    public boolean register(String username, String password) {
        //加密
        String md5String=Md5Util.getMD5String(password);
        userDao.insert(username,md5String);
        return true;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
