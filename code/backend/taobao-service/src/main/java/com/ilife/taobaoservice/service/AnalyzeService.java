package com.ilife.taobaoservice.service;

import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.User;

public interface AnalyzeService {
    Item setCategory(Item item);
    int updateCategory(User user);
}