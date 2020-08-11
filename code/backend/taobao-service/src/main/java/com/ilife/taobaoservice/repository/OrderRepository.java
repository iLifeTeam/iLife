package com.ilife.taobaoservice.repository;

import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.Query;
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
