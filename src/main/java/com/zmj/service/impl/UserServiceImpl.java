package com.zmj.service.impl;

import com.zmj.dao.UserDao;
import com.zmj.domain.User;
import com.zmj.service.UserService;
import com.zmj.utils.Md5Util;
import com.zmj.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> calims = ThreadLocalUtil.get();
        Integer id= (Integer) calims.get("id");
        userDao.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String new_pwd) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        String md5String = Md5Util.getMD5String(new_pwd);
        userDao.updatePwd(md5String,id);
    }
}
