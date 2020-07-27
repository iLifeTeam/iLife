package com.ilife.taobaoservice.dao;

import com.ilife.taobaoservice.entity.User;

public interface UserDao {
    User findByUsername(String username);
    User save(User user);
}
