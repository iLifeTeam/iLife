package com.ilife.weiboservice.dao.daoimpl;

import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.repository.WeiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class WeiboDaoImpl implements WeiboDao {

    @Autowired
    private WeiboRepository weiboRepository;

    @Override
    public Weibo findById(String id) {
        return weiboRepository.findAllById(id);
    }

    @Override
    public List<Weibo> findAllByUid(Long uid) {
        return weiboRepository.findAllByUid(uid);
    }

    @Override
    public void deleteByUid(Long uid) {
        weiboRepository.deleteByUid(uid);
    }

    @Override
    public void deleteById(String id) {
        weiboRepository.deleteById(id);
    }

    @Override
    public Weibo save(Weibo weibo) {
        weiboRepository.save(weibo);
        return weibo;
    }

    public List<Weibo> findLimits(Long uid, Timestamp startTime, Timestamp endTime){
        return weiboRepository.findLimits(uid,startTime,endTime);
    }
}
