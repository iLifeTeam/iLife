package com.ilife.musicservice.service.serviceimpl;

import com.ilife.musicservice.crawler.NetEaseCrawler;
import com.ilife.musicservice.dao.WyyhistoryDao;
import com.ilife.musicservice.entity.wyyuser;
import com.ilife.musicservice.repository.WyyhistoryRepository;
import com.ilife.musicservice.service.WyyhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WyyhistoryServiceImpl implements WyyhistoryService {
    @Autowired
    private WyyhistoryDao wyyhistoryDao;


    public List<wyyuser> findAllbyid(Long id){
        return wyyhistoryDao.findAllbyid(id);
    }
    public Page<wyyuser> findAllbyid(Long id, Pageable pageable){
        return wyyhistoryDao.findAllbyid(id,pageable);
    }


//    public void deletebyid(Long id) {
//        wyyhistoryDao.deletebywyyid(id);
//    }
}