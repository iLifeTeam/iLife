package com.ilife.authservice.repository;

import com.ilife.authservice.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<Users, Integer> {

    Users findById(Long id);

    Users findByNickname(String nickname);

    @Transactional
    @Modifying
    void deleteById(Long uid);

    @Transactional
    @Modifying
    @Query(value="update users set wyyid=?2 where id =?1")
    void updateWyyId(Long id, Long wyyId);
}
