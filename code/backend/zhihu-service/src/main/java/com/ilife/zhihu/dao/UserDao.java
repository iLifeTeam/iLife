package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.User;

public interface UserDao {
    User findById(String id);

    User findByName(String name);

    User findByEmail(String email);

    User findByPhone(String phone);

    User save(User user);

    void deleteById(String id);
}
