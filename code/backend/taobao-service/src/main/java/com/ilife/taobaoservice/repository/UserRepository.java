package com.ilife.taobaoservice.repository;

import com.ilife.taobaoservice.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
