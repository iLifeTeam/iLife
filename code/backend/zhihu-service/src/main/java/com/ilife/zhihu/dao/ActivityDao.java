package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Activity;

import java.util.List;

public interface ActivityDao {
    List<Activity> findAllActivityByZhihuId(String zhihuId);
    List<Activity> findAllActivityByZhihuIdAndType(String zhihuId, String type);
    Activity findById(Integer id);
    Activity save(Activity activity);

    void deleteByZhihuId(String zhihuId);
    void deleteById(Integer id);
}
