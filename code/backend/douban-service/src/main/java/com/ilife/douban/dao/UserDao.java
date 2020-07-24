package com.ilife.douban.dao;

import com.ilife.douban.entity.User;

public interface UserDao {
    User findById(String id);

    void deleteById(String id);
}
