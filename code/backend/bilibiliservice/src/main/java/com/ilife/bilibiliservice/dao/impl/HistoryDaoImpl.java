package com.ilife.bilibiliservice.dao.impl;

import com.ilife.bilibiliservice.dao.HistoryDao;
import com.ilife.bilibiliservice.entity.history;
import com.ilife.bilibiliservice.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryDaoImpl implements HistoryDao {
    @Autowired
    private HistoryRepository historyRepository;

    public List<history> findAllByMid(Long mid){
        return historyRepository.findAllByMid(mid);
    }

    public void addhistory(Long mid,Long oid,String type,Boolean is_fav){
        historyRepository.addhistory(mid,oid,type,is_fav);
    }
    public void deleteAllbymid(Long mid)
    {
        historyRepository.deleteAllByMid(mid);
    }
    public Page<history> findAllByMid(Long mid, Pageable pageable)
    {
        return historyRepository.findAllByMid(mid,pageable);
    }
    public List<String> getFavoriteTag(Long id){
        return historyRepository.getFavoriteTag(id);
    }
    public List<Long> getFavoriteUp(Long id){
        return historyRepository.getFavoriteUp(id);
    }
}
