package com.ilife.jingdongservice.controller;

import com.alibaba.fastjson.JSON;
import com.ilife.jingdongservice.entity.Item;
import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;
import com.ilife.jingdongservice.repository.UserRepository;
import com.ilife.jingdongservice.service.CrawlerService;
import com.ilife.jingdongservice.service.JingDongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class JingdongControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    JingDongService jingDongService;
    @MockBean
    CrawlerService crawlerService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void loginIntoJingdong() throws Exception {
        String username = "name";
        User user = new User("name", new Date(0L));
        when(jingDongService.getUserByUsername(username)).thenReturn(user);
        when(jingDongService.saveUser(Mockito.any(User.class))).thenReturn(user);
        when(crawlerService.login(username)).thenReturn("qr code base64 string");
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/login/qrcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc
                .perform(loginRequest)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals("qr code base64 string", response);

        String username1 = "name";
        when(jingDongService.getUserByUsername(username1)).thenReturn(null);
        MockHttpServletRequestBuilder loginRequest1 =
                MockMvcRequestBuilders.post("/login/qrcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username1);
        String response1 = mockMvc
                .perform(loginRequest1)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals("qr code base64 string", response1);
    }

    @Test
    void loginCheck() throws Exception {
        String username = "name";
        when(crawlerService.loginCheck(username)).thenReturn(true);
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.get("/login/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc
                .perform(loginRequest)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals("true", response);
    }

    @Test
    void updateOrderAll() throws Exception {
        String username = "username";
        when(crawlerService.fetchHistory(username)).thenReturn(100);
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/order/crawl/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertEquals(100, Integer.parseInt(response));
    }

    @Test
    void updateOrderAfter() throws Exception {
        String username = "username";
        Date date = new Date(0L);
        when(crawlerService.fetchHistoryAfter(username,date)).thenReturn(0);
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/order/crawl/after")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username)
                        .param("date",date.toLocalDate().toString());
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertEquals(0, Integer.parseInt(response));
    }

    @Test
    void updateOrderIncremental() throws Exception {
        String username = "username";
        Date date = new Date(0L);
        User user = new User("name",  new Date(0L));
        when(jingDongService.getUserByUsername(username)).thenReturn(user);
        when(crawlerService.fetchHistoryAfter(username,date)).thenReturn(100);
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/order/crawl/incremental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertEquals(100, Integer.parseInt(response));
    }

    @Test
    void getAllOrder() throws Exception {
        String username = "username";
        User user = new User("name", new Date(0L));
        when(jingDongService.getUserByUsername(username)).thenReturn(user);
        List<Order> orders = new ArrayList<>();
        Order order = new Order(0L, new Date(Long.MAX_VALUE), 100D,"shop", null, new ArrayList<>());
        order.getItems().add(new Item(0,"",0D,1,"url",order));
        orders.add(order);
        when(jingDongService.getOrderByUser(user)).thenReturn(orders);

        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.get("/order/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(response);
        Assertions.assertEquals(JSON.toJSONString(orders,true),response);
    }

    @Test
    void getOrderBetween() throws Exception {
        String username = "username";
        User user = new User("name", new Date(0L));
        Date low = new Date(0L);
        Date high = new Date(0L);
        when(jingDongService.getUserByUsername(username)).thenReturn(user);
        List<Order> orders = new ArrayList<>();
        Order order = new Order(0L, new Date(Long.MAX_VALUE), 100D,"shop", null, new ArrayList<>());
        order.getItems().add(new Item(0,"",0D,1,"url",order));
        orders.add(order);
        when(jingDongService.getOrderByUserAndDate(Mockito.any(User.class),Mockito.any(Date.class),Mockito.any(Date.class))).thenReturn(orders);

        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.get("/order/between")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username)
                        .param("low",low.toLocalDate().toString())
                        .param("high",high.toLocalDate().toString());
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(JSON.toJSONString(orders,true),response);
    }

    @Test
    void getUser() throws Exception {
        String username = "username";
        User user = new User("name", new Date(0L));
        when(jingDongService.getUserByUsername(username)).thenReturn(user);
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(JSON.toJSONString(user,true),response);

    }
}