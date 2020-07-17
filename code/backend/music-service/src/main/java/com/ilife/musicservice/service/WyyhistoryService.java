package com.ilife.musicservice.service;

import com.ilife.musicservice.entity.wyyuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WyyhistoryService {
    List<wyyuser> findAllbyid(Long id);
    Page<wyyuser> findAllbyid(Long id, Pageable pageable);
//    void deletebyid(Long id);
}
