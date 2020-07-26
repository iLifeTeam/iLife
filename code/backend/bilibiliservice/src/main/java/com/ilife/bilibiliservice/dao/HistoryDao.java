package com.ilife.bilibiliservice.dao;

import com.ilife.bilibiliservice.entity.history;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryDao {
    List<history> findAllByMid(Long mid);
    Page<history> findAllByMid(Long mid, Pageable pageable);
    void addhistory(Long mid,Long oid,String type,Boolean is_fav);
    void deleteAllbymid(Long mid);
}
