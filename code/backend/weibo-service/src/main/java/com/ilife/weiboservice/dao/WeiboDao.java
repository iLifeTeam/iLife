package com.ilife.weiboservice.dao;

import com.ilife.weiboservice.entity.Weibo;

import java.util.List;

public interface WeiboDao {
    Weibo findById(String id);

    List<Weibo> findAllByUid(Integer uid);

    void deleteByUid(Integer uid);

    void deleteById(Integer id);

}
