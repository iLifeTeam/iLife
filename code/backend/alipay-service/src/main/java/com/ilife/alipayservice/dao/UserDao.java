package com.ilife.alipayservice.dao;

import com.ilife.alipayservice.entity.User;



public interface UserDao {
    User findByPhone(String phone);
    User findById(Integer id);
    User save(User user);
}
