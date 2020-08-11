package com.ilife.taobaoservice.dao.daoimpl;

import com.ilife.taobaoservice.dao.ItemDao;
import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.repository.ItemRepository;
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
