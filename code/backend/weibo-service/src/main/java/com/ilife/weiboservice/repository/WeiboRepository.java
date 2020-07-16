package com.ilife.weiboservice.repository;

import com.ilife.weiboservice.entity.Weibo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface WeiboRepository extends CrudRepository<Weibo,Integer> {


    Weibo findById(String id);

    List<Weibo> findAllByUid(Integer uid);

    @Transactional
    @Modifying
    void deleteByUid(Integer uid);

    @Transactional
    @Modifying
    void deleteById(Integer id);


}
