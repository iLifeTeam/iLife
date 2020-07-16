package com.ilife.alipayservice.repository;

import com.ilife.alipayservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByPhone(String phone);
    User findByUsername(String username);
}
