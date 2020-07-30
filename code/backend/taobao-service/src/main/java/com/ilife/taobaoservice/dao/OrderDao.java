package com.ilife.taobaoservice.dao;

import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;

import java.util.Date;
import java.util.List;

public interface OrderDao {

    List<Order> findByUserAndDate(User user, Date low, Date high);
    List<Order> findByUser(User user);
    List<Order> findByUserAndShop(User user, String shop);
    Order findById(Long id);
    Order save(Order order);
}
