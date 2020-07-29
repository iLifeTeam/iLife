package com.ilife.taobaoservice.service;

import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.Stats;
import com.ilife.taobaoservice.entity.User;

import java.sql.Date;
import java.util.List;

public interface TaobaoService {
    User getUserByUsername(String username);
    List<Order> getOrderByUserAndDate(User user, Date low, Date high);
    List<Order> getOrderByUserAndShop(User user, String shop);
    List<Order> getOrderByUser(User user);
    Stats getStats(User user);
    User saveUser(User user);
}
