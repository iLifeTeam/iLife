package com.ilife.bilibiliservice.dao;

import com.ilife.bilibiliservice.entity.video;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VideoDao {
    List<video> findAllByOidAndType(Long oid,String type);
    void addvideo(Long oid,String type, String auther_name,Long auther_id,String tag_name,String title);
}
