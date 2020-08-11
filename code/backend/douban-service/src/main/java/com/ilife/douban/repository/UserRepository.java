package com.ilife.douban.repository;

import com.ilife.douban.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, String> {


    User findAllById(String id);

    @Transactional
    @Modifying
    void deleteById(String id);


}
