package com.ilife.zhihu.repository;

import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByName(String name);
    User findByEmail(String email);
    User findByPhone(String phone);
    @Transactional
    @Modifying
    void deleteByUid(String uid);
}
