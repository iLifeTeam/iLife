package com.ilife.taobaoservice.repository;

import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
