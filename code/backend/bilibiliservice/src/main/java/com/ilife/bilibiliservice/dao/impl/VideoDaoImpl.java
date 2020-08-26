package com.ilife.bilibiliservice.dao.impl;

import com.ilife.bilibiliservice.dao.VideoDao;
import com.ilife.bilibiliservice.entity.video;
import com.ilife.bilibiliservice.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoDaoImpl implements VideoDao {
    @Autowired
    private VideoRepository videoRepository;
    public List<video> findAllByOidAndType(Long oid,String type)
    {
        return videoRepository.findAllByOidAndType(oid,type);
    }
    public void addvideo(Long oid,String type, String auther_name,Long auther_id,String tag_name,String title){
        videoRepository.addvideo(oid,type,auther_name,auther_id,tag_name,title);
    }
}
