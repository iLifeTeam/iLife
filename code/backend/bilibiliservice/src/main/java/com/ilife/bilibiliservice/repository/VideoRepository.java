package com.ilife.bilibiliservice.repository;

import com.ilife.bilibiliservice.entity.video;
import com.ilife.bilibiliservice.entity.videokey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoRepository extends JpaRepository<video, videokey> {
    List<video> findAllByOidAndType(Long oid,String type);
    @Transactional
    @Modifying
    @Query(value = "insert into video(oid,`type`,auther_name,auther_id,tag_name,title) values(?1, ?2, ?3,?4,?5,?6)",nativeQuery = true)
    void addvideo(Long oid,String type, String auther_name,Long auther_id,String tag_name,String title);
}
