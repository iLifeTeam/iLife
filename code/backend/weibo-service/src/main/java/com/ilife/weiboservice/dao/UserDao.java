package com.ilife.weiboservice.dao;

import com.ilife.weiboservice.entity.User;


public interface UserDao {
    User findByUid(Integer uid);

    User findByNickname(String nickname);

    void deleteByUid(Integer uid);

    User save(User user);

}
