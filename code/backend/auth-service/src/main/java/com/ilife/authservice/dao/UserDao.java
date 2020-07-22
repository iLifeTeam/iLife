package com.ilife.authservice.dao;

import com.ilife.authservice.entity.Users;

public interface UserDao {
    Users findById(Long id);

    Users findByNickname(String nickname);

    void deleteById(Long id);

    void save(Users user);

    int updateWyyId(Long id, Long wyyId);

    int updateWbId(Long id, Long wbId);

    int updateZhId(Long id, String zhId);

    Users findByAccount(String account);
}
