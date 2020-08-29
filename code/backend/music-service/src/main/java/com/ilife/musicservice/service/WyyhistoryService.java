package com.ilife.musicservice.service;

import com.alibaba.fastjson.JSONArray;
import com.ilife.musicservice.entity.wyyuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface WyyhistoryService {
    List<wyyuser> findAllbyid(Long id);
    Page<wyyuser> findAllbyid(Long id, Pageable pageable);
//    void deletebyid(Long id);

    JSONArray getFavorSingers(Long id);
}
