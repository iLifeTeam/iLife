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

    public Weibo findById(String id) {
        return weiboRepository.findById(id);
    }

    public List<Weibo> findAllByUid(Integer uid) {
        return weiboRepository.findAllByUid(uid);
    }

    public void deleteByUid(Integer uid) {
        weiboRepository.deleteByUid(uid);
    }

    public void deleteById(Integer id) {
        weiboRepository.deleteById(id);
    }

    public Weibo save(Weibo weibo) {
        weiboRepository.save(weibo);
        return weibo;
    }
}
