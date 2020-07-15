package com.ilife.weiboservice.service;

import com.ilife.weiboservice.entity.Weibo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WeiboService {
    List<Weibo> findAllByUid(Integer uid);

    void crawlWeibo(Long uid);

    Weibo findById(String id);

    ResponseEntity<?> deleteByUid(Integer uid);

    ResponseEntity<?> deleteById(Integer id);
    
    ResponseEntity<?> save(Weibo weibo);
}
