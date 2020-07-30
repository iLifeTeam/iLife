package com.ilife.taobaoservice.repository;

import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

}
