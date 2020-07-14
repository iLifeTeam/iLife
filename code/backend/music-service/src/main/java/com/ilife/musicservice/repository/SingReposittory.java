package com.ilife.musicservice.repository;

import com.ilife.musicservice.entity.sing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SingReposittory extends JpaRepository<sing,Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into sing(m_id,s_id,sname) values(?1, ?2, ?3)",nativeQuery = true)
    void addsing(Long mid,Long sid, String name);
}
