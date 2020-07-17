package com.ilife.musicservice.dao;

import com.ilife.musicservice.entity.wyyuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface WyyhistoryDao {
    List<wyyuser> findAllbyid(Long id);
    Page<wyyuser> findAllbyid(Long id, Pageable pageable);
    void addhistory(Long wid,Long mid, Integer playcount,Integer score);

}
