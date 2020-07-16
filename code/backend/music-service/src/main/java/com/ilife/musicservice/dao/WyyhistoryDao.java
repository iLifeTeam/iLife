package com.ilife.musicservice.dao;

import com.ilife.musicservice.entity.wyyuser;

import java.util.List;

public interface WyyhistoryDao {
    List<wyyuser> findAllbyid(Long id);
    void addhistory(Long wid,Long mid, Integer playcount,Integer score);

}
