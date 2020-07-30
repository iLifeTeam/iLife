package com.ilife.weiboservice.dao;

import com.ilife.weiboservice.entity.User;

public interface UserDao {
    User findAllById(Long id);

    User findByNickname(String nickname);

    void deleteById(Long id);

    User save(User user);
}
