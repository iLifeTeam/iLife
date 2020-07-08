package com.ilife.weiboservice.repository;

import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.entity.Weibo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface userRepository {

    @Query("from User where u_id=?1")
    User findByUid(Integer uid);

}
