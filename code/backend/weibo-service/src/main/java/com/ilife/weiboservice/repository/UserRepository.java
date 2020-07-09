package com.ilife.weiboservice.repository;

import com.ilife.weiboservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User,Integer> {


    User findByUid(Integer uid);

    User findByNickname(String nickname);

    @Transactional
    @Modifying
    void deleteByUid(Integer uid);





}