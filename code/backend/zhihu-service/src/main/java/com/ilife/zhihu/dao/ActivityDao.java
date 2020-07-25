package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Activity;

public interface ActivityDao {
    Activity findById(String id);

    Activity save(Activity activity);

    void deleteById(String id);
}
