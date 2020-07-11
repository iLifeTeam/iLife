package com.ilife.weiboservice.service;

import com.ilife.weiboservice.entity.Weibo;

import java.util.List;

public interface WeiboService {
    List<Weibo> findAllByUid(Integer uid);

    void crawlWeibo(Long uid);
}
