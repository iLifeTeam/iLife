package com.ilife.authservice.dao;

import com.ilife.authservice.entity.Users;

public interface UserDao {
    Users findById (Long id) ;
    Users findByNickname(String nickname);

    void deleteById(Long id);
    void save(Users user);
}
