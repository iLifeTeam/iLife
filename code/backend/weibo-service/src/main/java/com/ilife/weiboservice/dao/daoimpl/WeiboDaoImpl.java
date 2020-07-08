package com.ilife.weiboservice.dao.daoimpl;

import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.repository.WeiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeiboDaoImpl implements WeiboDao {

    @Autowired
    private WeiboRepository weiboRepository;

    public Weibo findByWid(Integer wid){
        return weiboRepository.findByWid(wid);
    }

    public List<Weibo> findAllByUid(Integer uid){
        return weiboRepository.findAllByUid(uid);
    }

    public void deleteByUid(Integer uid){
        weiboRepository.deleteByUid(uid);
    }

    public void deleteByWid(Integer wid){
        weiboRepository.deleteByWid(wid);
    }

    public Weibo save(Weibo weibo){
        weiboRepository.save(weibo);
        return weibo;
    }
}
