package com.ilife.taobaoservice.service.serviceimpl;

import com.ilife.taobaoservice.dao.ItemDao;
import com.ilife.taobaoservice.dao.OrderDao;
import com.ilife.taobaoservice.dao.UserDao;
import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.service.CrawlerService;
import com.ilife.taobaoservice.service.TaobaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TaobaoServiceImpl implements TaobaoService {
    @Autowired
    UserDao userDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<Order> getOrderByUserAndDate(User user, Date low, Date high) {
        return orderDao.findByUserAndDate(user,low,high);
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        return orderDao.findByUser(user);
    }

    @Override
    public List<Order> getOrderByUserAndShop(User user, String shop) {
        return orderDao.findByUserAndShop(user,shop);
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }
}
