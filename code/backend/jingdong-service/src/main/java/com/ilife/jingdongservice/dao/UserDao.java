package com.ilife.jingdongservice.dao;

import com.ilife.jingdongservice.entity.User;

public interface UserDao {
    User findByUsername(String username);
    User save(User user);
}
