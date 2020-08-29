package com.ilife.musicservice.repository;

import com.ilife.musicservice.entity.musics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MusicsRepository extends JpaRepository<musics,Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into musics(m_id,mname) select ?1, ?2 from dual where not exists (select m_id from musics where m_id = ?1)",nativeQuery = true)
    void addmusic(Long id, String name);


    @Query(value = "select m_id\n" +
            "from wyyuser\n" +
            "WHERE score in (\n" +
            "SELECT MAX(score)\n" +
            "FROM wyyuser\n" +
            "where wyyid=?1)\n" +
            "and wyyid=?1",nativeQuery = true)
    Long getFavoriteSong(Long id);


}
