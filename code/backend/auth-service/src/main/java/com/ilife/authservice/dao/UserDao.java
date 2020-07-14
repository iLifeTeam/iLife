package com.ilife.authservice.dao;

import com.ilife.authservice.entity.Users;

public interface UserDao {
    Users findById(Long id);

    Users findByNickname(String nickname);

    void deleteById(Long id);

    void save(Users user);

    void updateWyyId(Long id, Long wyyId);

    void updateWbId(Long id, Long wbId);

    void updateZhId(Long id, String zhId);

    Users findByAccount(String account);
}
