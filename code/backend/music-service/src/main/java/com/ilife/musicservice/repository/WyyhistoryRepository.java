package com.ilife.musicservice.repository;

import com.ilife.musicservice.entity.wyyuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WyyhistoryRepository extends JpaRepository<wyyuser,Long> {
    List<wyyuser> findAllByWyyid(Long id);

    @Transactional
    @Modifying
    @Query(value = "insert into wyyuser(wyyid,m_id,playcount,score) values(?1, ?2, ?3,?4)",nativeQuery = true)
    void addhistory(Long wid,Long mid, Integer playcount,Integer score);
}
