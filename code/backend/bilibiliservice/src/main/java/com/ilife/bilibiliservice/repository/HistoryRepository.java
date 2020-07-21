package com.ilife.bilibiliservice.repository;

import com.ilife.bilibiliservice.entity.history;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistoryRepository extends JpaRepository<history,Long> {
    List<history> findAllByMid(Long mid);
    @Transactional
    @Modifying
    @Query(value = "insert into history(mid,oid,`type`,is_fav) values(?1, ?2,?3,?4)",nativeQuery = true)
    void addhistory(Long mid,Long oid,String type,Boolean is_fav);
}
