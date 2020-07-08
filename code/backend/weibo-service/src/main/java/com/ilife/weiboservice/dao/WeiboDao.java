package com.ilife.weiboservice.dao;

import com.ilife.weiboservice.entity.Weibo;
import java.util.List;

public interface WeiboDao {
    Weibo findByWid(Integer wid);

    List<Weibo> findAllByUid(Integer uid);

    void deleteByUid(Integer uid);

    void deleteByWid(Integer wid);

    Weibo save(Weibo weibo);
}
