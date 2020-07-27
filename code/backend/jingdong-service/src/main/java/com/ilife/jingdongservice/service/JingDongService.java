package com.ilife.jingdongservice.service;

import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;

import java.sql.Date;
import java.util.List;

public interface JingDongService {
    User getUserByUsername(String username);
    List<Order> getOrderByUserAndDate(User user, Date low, Date high);
    List<Order> getOrderByUserAndShop(User user, String shop);
    List<Order> getOrderByUser(User user);

    User saveUser(User user);
}
