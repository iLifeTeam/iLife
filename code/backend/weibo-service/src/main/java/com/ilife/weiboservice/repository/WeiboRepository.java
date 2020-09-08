package com.ilife.weiboservice.repository;

import com.ilife.weiboservice.entity.Weibo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface WeiboRepository extends CrudRepository<Weibo, String> {


    Weibo findAllById(String id);
    List<Weibo> findAllByUid(Long uid);
    Page<Weibo> findAllByUid(Long uid, Pageable p);


    @Transactional
    @Modifying
    void deleteByUid(Long uid);

    @Transactional
    @Modifying
    void deleteById(String id);

    @Query(value="from Weibo where uid = ?1 and publish_time > ?2 and publish_time < ?3")
    List<Weibo> findLimits(Long uid, Timestamp startTime,Timestamp endTime);


}
