package com.ilife.musicservice.dao.daoimpl;

import com.ilife.musicservice.dao.WyyhistoryDao;
import com.ilife.musicservice.entity.wyyuser;
import com.ilife.musicservice.repository.WyyhistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WyyhistoryDaoImpl implements WyyhistoryDao {
    @Autowired
    private WyyhistoryRepository wyyhistoryRepository;

    public List<wyyuser> findAllbyid(Long id){
        return wyyhistoryRepository.findAllByWyyid(id);
    }
    public void addhistory(Long wid,Long mid, Integer playcount,Integer score){
        wyyhistoryRepository.addhistory(wid, mid, playcount, score);
    }
}
