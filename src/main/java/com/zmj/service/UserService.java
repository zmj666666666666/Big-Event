package com.zmj.service;

import com.zmj.domain.User;

public interface UserService {
    User findByUsername(String username);
    boolean register(String username,String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String new_pwd);
}
