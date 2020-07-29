package com.ilife.taobaoservice.service.serviceimpl;

import com.ilife.taobaoservice.dao.ItemDao;
import com.ilife.taobaoservice.dao.OrderDao;
import com.ilife.taobaoservice.dao.UserDao;
import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.Stats;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.service.CrawlerService;
import com.ilife.taobaoservice.service.TaobaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Order getOrderById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public Stats getStats(User user) {
        Stats stats = new Stats();
        Map<String, Stats.Category> categoryMap = new HashMap<>();
        List<Order> orders = orderDao.findByUser(user);
        Order mostExpensive = new Order(0L,null,0D,null,null,null);
        LocalDate today = LocalDate.now();
        LocalDate monthBegin = today.withDayOfMonth(1);
        LocalDate yearBegin = today.withDayOfYear(1);
        Double monthExpense = 0D, yearExpense = 0D;
        for (Order order: orders){
            if (order.getTotal() > mostExpensive.getTotal()){
                mostExpensive = order;
            }
            if (order.getDate().toLocalDate().isAfter(monthBegin)){
                monthExpense += order.getTotal();
            }
            if (order.getDate().toLocalDate().isAfter(yearBegin)){
                yearExpense += order.getTotal();
            }
            String cate = order.getItems().get(0).getFirstCategory();
            if (!categoryMap.containsKey(cate)){
                categoryMap.put(cate, new Stats.Category(new ArrayList<>(),0D));
            }
            categoryMap.get(cate).addOrder(order);
        }
        stats.setMostExpensive(mostExpensive);
        stats.setExpenseMonth(monthExpense);
        stats.setExpenseYear(yearExpense);
        stats.setCategories(categoryMap);
        return stats;
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
