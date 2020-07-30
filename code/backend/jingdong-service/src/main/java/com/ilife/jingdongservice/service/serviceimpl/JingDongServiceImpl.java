package com.ilife.jingdongservice.service.serviceimpl;

import com.ilife.jingdongservice.service.JingDongService;
import com.ilife.jingdongservice.dao.ItemDao;
import com.ilife.jingdongservice.dao.OrderDao;
import com.ilife.jingdongservice.dao.UserDao;
import com.ilife.jingdongservice.entity.Item;
import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;
import com.ilife.jingdongservice.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class JingDongServiceImpl implements JingDongService {
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
