package com.ilife.weiboservice.repository;

import com.ilife.weiboservice.entity.Weibo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface WeiboRepository extends CrudRepository<Weibo, String> {


    Weibo findAllById(String id);

    List<Weibo> findAllByUid(Long uid);

    @Transactional
    @Modifying
    void deleteByUid(Long uid);

    @Transactional
    @Modifying
    void deleteById(String id);


}
