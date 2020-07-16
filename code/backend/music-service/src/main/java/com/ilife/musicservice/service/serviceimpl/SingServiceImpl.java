package com.ilife.musicservice.service.serviceimpl;

import com.ilife.musicservice.dao.SingDao;
import com.ilife.musicservice.service.SingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingServiceImpl implements SingService {
    @Autowired
    private SingDao singDao;
    public  void addsing(Long mid,Long sid, String name)
    {
        singDao.addsing(mid, sid, name);
    }

}
