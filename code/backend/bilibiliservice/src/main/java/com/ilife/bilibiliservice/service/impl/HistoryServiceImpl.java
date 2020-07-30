package com.ilife.bilibiliservice.service.impl;

import com.ilife.bilibiliservice.dao.HistoryDao;
import com.ilife.bilibiliservice.entity.history;
import com.ilife.bilibiliservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryDao historyDao;


    public List<history> findAllByMid(Long mid){
        return historyDao.findAllByMid(mid);
    }
    public Page<history> findAllByMid(Long mid, Pageable pageable)
    {
        return historyDao.findAllByMid(mid,pageable);
    }
}
