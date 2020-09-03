package com.ilife.douban.dao;

import com.ilife.douban.entity.Recommendation;


public interface RcmdDao {
    Recommendation findById(String id);

    void save(Recommendation rcmd);
}
