package com.ilife.taobaoservice.dao.daoimpl;

import com.ilife.taobaoservice.dao.OrderDao;
import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Order> findByUserAndDate(User user, Date low, Date high) {
        return orderRepository.findByUserAndDateBetween(user,low,high);
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> findByUserAndShop(User user, String shop) {
        return orderRepository.findByUserAndShop(user,shop);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
