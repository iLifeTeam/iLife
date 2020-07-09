package com.ilife.weiboservice.repository;

import com.ilife.weiboservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User,Integer> {

    //这里findById会报错，所以改了一下
    User findAllById(Integer id);

    User findByNickname(String nickname);

    @Transactional
    @Modifying
    void deleteById(Integer uid);





}
