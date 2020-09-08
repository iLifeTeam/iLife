package com.ilife.bilibiliservice.repository;

import com.ilife.bilibiliservice.entity.history;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistoryRepository extends JpaRepository<history,Long> {
    List<history> findAllByMid(Long mid);
    Page<history> findAllByMid(Long mid, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "insert into history(mid,oid,`type`,is_fav) select ?1, ?2,?3,?4 from dual where not exists (select * from history where (mid,oid,`type`)=(?1,?2,?3))",nativeQuery = true)
    void addhistory(Long mid,Long oid,String type,Boolean is_fav);

    @Transactional
    @Modifying
    int deleteAllByMid(Long mid);


    @Query(value = "SELECT tag_name\n" +
            "from(\n" +
            "select tag_name,COUNT(tag_name) as times\n" +
            "from video NATURAL join history\n" +
            "where mid = ?1\n" +
            "GROUP BY tag_name\n" +
            ") as T\n" +
            "where times >= 5",nativeQuery = true)
    List<String> getFavoriteTag(Long id);


    @Query(value = "SELECT auther_id\n" +
            "from(\n" +
            "select auther_id,COUNT(auther_id) as times\n" +
            "from video NATURAL join history\n" +
            "where mid = ?1\n" +
            "GROUP BY auther_id\n" +
            ") as T\n" +
            "where times >= 5",nativeQuery = true)
    List<Long> getFavoriteUp(Long id);




}
