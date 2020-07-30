package com.ilife.weiboservice.service;

import com.ilife.weiboservice.entity.Statistics;
import com.ilife.weiboservice.entity.Weibo;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface WeiboService {
    List<Weibo> findAllByUid(Long uid);

    Weibo findById(String id);

    ResponseEntity<?> deleteByUid(Long uid);

    ResponseEntity<?> deleteById(String id);

    ResponseEntity<?> save(Weibo weibo);

    Statistics getStats(Long uid, Date startDate,Date endDate);
}
