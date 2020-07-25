package com.ilife.jingdongservice.repository;

import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserAndShop(User user, String shop);
    List<Order> findByUserAndDateBetween(User user, Date low, Date high);
}
