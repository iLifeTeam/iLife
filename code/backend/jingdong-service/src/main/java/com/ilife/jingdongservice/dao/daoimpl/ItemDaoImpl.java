package com.ilife.jingdongservice.dao.daoimpl;

import com.ilife.jingdongservice.dao.ItemDao;
import com.ilife.jingdongservice.entity.Item;
import com.ilife.jingdongservice.repository.ItemRepository;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
