package com.ilife.weiboservice.dao;

import com.ilife.weiboservice.entity.User;


public interface UserDao{
    User findAllById(Integer id);

    User findByNickname(String nickname);

    void deleteById(Integer id);

    User save(User user);

}
