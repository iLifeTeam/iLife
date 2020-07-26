package com.ilife.jingdongservice.repository;

import com.ilife.jingdongservice.entity.Item;
import com.ilife.jingdongservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
}
