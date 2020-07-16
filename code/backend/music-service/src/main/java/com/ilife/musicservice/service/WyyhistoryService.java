package com.ilife.musicservice.service;

import com.ilife.musicservice.entity.wyyuser;

import java.util.List;

public interface WyyhistoryService {
    List<wyyuser> findAllbyid(Long id);
    void crawlWyy(String ph,String pw);
}
