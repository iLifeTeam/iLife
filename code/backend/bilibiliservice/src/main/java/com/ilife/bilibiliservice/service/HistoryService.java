package com.ilife.bilibiliservice.service;

import com.ilife.bilibiliservice.entity.history;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryService {
    List<history> findAllByMid(Long mid);
    Page<history> findAllByMid(Long mid, Pageable pageable);
    List<String> getFavoriteTag(Long id);
    int getFavoriteTagid(String s);
    List<Long> getFavoriteUp(Long id);
}
