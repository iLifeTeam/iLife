package com.ilife.taobaoservice.service;

import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.repository.ItemRepository;
import com.ilife.taobaoservice.repository.OrderRepository;
import com.ilife.taobaoservice.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class TaobaoServiceTest {

    @Autowired
    TaobaoService taobaoService;
    @MockBean
    ItemRepository itemRepository;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    UserRepository userRepository;


    @Test
    void getUserByUsername() {
        String username = "name";
        User user = new User("uid", "password", new Date(0L));
        when(userRepository.findById(username)).thenReturn(java.util.Optional.of(user));
        Assertions.assertEquals(user, taobaoService.getUserByUsername(username));

    }

    @Test
    void getOrderByUserAndDate() {
        User user = new User("uid", "password", new Date(0L));
        Date low = new Date(0L);
        Date high = new Date(0L);
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByUserAndDateBetween(user,low,high)).thenReturn(orders);
        Assertions.assertEquals(orders, taobaoService.getOrderByUserAndDate(user,low,high));
    }

    @Test
    void getOrderByUserAndShop() {
        User user = new User("uid", "password", new Date(0L));
        String shop = "shop";
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByUserAndShop(user,shop)).thenReturn(orders);
        Assertions.assertEquals(orders, taobaoService.getOrderByUserAndShop(user,shop));
    }

    @Test
    void getOrderByUser() {
        User user = new User("uid", "password", new Date(0L));
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByUser(user)).thenReturn(orders);
        Assertions.assertEquals(orders, taobaoService.getOrderByUser(user));
    }

    @Test
    void getOrderById() {
        Long oid = 0L;
        Order order = new Order();
        when(orderRepository.findById(oid)).thenReturn(Optional.of(order));
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Assertions.assertEquals(order, taobaoService.getOrderById(oid));
    }

    @Test
    void getStats() {
        User user = new User("uid", "password", new Date(0L));
        List<Order> orders = new ArrayList<>();
        Order order = new Order(0L, new Date(Long.MAX_VALUE), 100D,"shop", null, new ArrayList<>());
        order.getItems().add(new Item(0,"",0D,1,"url","cate1","cate2","cate3",order));
        orders.add(order);
        when(orderRepository.findByUser(user)).thenReturn(orders);
        taobaoService.getStats(user);
    }

    @Test
    void saveUser() {
        User user = new User("uid", "password", new Date(0L));
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Assertions.assertEquals(user, taobaoService.saveUser(user));
    }
}