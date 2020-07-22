package com.ilife.weiboservice.dao;

import com.ilife.weiboservice.entity.Weibo;

import java.util.List;

public interface WeiboDao {
    Weibo findById(String id);

    List<Weibo> findAllByUid(Long uid);

    void deleteByUid(Long uid);

    void deleteById(String id);

    Weibo save(Weibo weibo);
}
