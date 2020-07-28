package com.ilife.bilibiliservice.dao.impl;

import com.ilife.bilibiliservice.dao.BiliUserDao;
import com.ilife.bilibiliservice.entity.biliuser;
import com.ilife.bilibiliservice.repository.BiliuserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BiliUserDaoImpl implements BiliUserDao {
    @Autowired
    private BiliuserRepostory biliuserRepostory;
    public void addUser(biliuser biliuser){
        biliuserRepostory.save(biliuser);
    }
}
