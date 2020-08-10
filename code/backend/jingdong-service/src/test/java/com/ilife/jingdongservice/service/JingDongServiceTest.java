package com.ilife.jingdongservice.service;

import com.ilife.jingdongservice.JingdongServiceApplication;
import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;
import com.ilife.jingdongservice.repository.ItemRepository;
import com.ilife.jingdongservice.repository.OrderRepository;
import com.ilife.jingdongservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class JingDongServiceTest {

    @Autowired
    JingDongService jingdongService;
    @MockBean
    ItemRepository itemRepository;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    UserRepository userRepository;


    @Test
    void getUserByUsername() {
        String username = "name";
        User user = new User("uid", new Date(0L));
        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, jingdongService.getUserByUsername(username));
    }

    @Test
    void getOrderByUserAndDate() {
        User user = new User("uid", new Date(0L));
        Date low = new Date(0L);
        Date high = new Date(0L);
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByUserAndDateBetween(user,low,high)).thenReturn(orders);
        Assertions.assertEquals(orders, jingdongService.getOrderByUserAndDate(user,low,high));
    }

    @Test
    void getOrderByUserAndShop() {
        User user = new User("uid", new Date(0L));
        String shop = "shop";
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByUserAndShop(user,shop)).thenReturn(orders);
        Assertions.assertEquals(orders, jingdongService.getOrderByUserAndShop(user,shop));
    }

    @Test
    void getOrderByUser() {
        User user = new User("uid",new Date(0L));
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByUser(user)).thenReturn(orders);
        Assertions.assertEquals(orders, jingdongService.getOrderByUser(user));
    }

    @Test
    void saveUser() {
        User user = new User("uid", new Date(0L));
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Assertions.assertEquals(user, jingdongService.saveUser(user));
    }
}