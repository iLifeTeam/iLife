package com.ilife.musicservice.dao.daoimpl;

import com.ilife.musicservice.dao.SingDao;
import com.ilife.musicservice.repository.SingReposittory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class SingDaoImpl implements SingDao {
    @Autowired
    private SingReposittory singReposittory;
    public void addsing(Long mid,Long sid,String name){
        singReposittory.addsing(mid,sid,name);
    }
}
