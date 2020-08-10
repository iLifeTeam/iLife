package com.ilife.taobaoservice.controller;

import com.alibaba.fastjson.JSON;
import com.ilife.taobaoservice.entity.Item;
import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.Stats;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.repository.UserRepository;
import com.ilife.taobaoservice.service.AnalyzeService;
import com.ilife.taobaoservice.service.CrawlerService;
import com.ilife.taobaoservice.service.TaobaoService;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Primary;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class TaobaoControllerTest {


    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private TaobaoService taobaoService;
    @MockBean
    private CrawlerService crawlerService;
    @MockBean
    private AnalyzeService analyzeService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void fetchLoginSms() throws Exception {
        String username = "name";
        User user = new User("name", "password", new Date(0L));
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
        when(crawlerService.fetchSms(username)).thenReturn("success");
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/login/sms/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phone",username);
        String response = mockMvc
                .perform(loginRequest)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String username1 = "name1";
        when(crawlerService.fetchSms(username1)).thenReturn("success");
        when(taobaoService.getUserByUsername(username1)).thenReturn(null);
        when(taobaoService.saveUser(Mockito.any(User.class))).thenReturn(user);
        MockHttpServletRequestBuilder loginRequest1 =
                MockMvcRequestBuilders.post("/login/sms/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phone",username1);
        String response1 = mockMvc.perform(loginRequest1).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertEquals("success",response);
        assertEquals("success",response1);
    }

    @Test
    void loginWithSmsCode() throws Exception {
        String username = "name";
        User user = new User("name", "password", new Date(0L));
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
        when(crawlerService.loginWithSms(username,"sms")).thenReturn("success");
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/login/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phone",username)
                        .param("smsCode","sms");
        String response = mockMvc
                .perform(loginRequest)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String username1 = "name1";
        when(crawlerService.loginWithSms(username1,"sms")).thenReturn("success");
        when(taobaoService.getUserByUsername(username1)).thenReturn(null);
        when(taobaoService.saveUser(Mockito.any(User.class))).thenReturn(user);
        MockHttpServletRequestBuilder loginRequest1 =
                MockMvcRequestBuilders.post("/login/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phone",username1)
                        .param("smsCode","sms");
        String response1 = mockMvc.perform(loginRequest1).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertEquals("success",response);
        assertEquals("success",response1);
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
        User user = new User("name", "password", new Date(0L));
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
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
        User user = new User("name", "password", new Date(0L));
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
        List<Order> orders = new ArrayList<>();
        Order order = new Order(0L, new Date(Long.MAX_VALUE), 100D,"shop", null, new ArrayList<>());
        order.getItems().add(new Item(0,"",0D,1,"url","cate1","cate2","cate3",order));
        orders.add(order);
        when(taobaoService.getOrderByUser(user)).thenReturn(orders);

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
        User user = new User("name", "password", new Date(0L));
        Date low = new Date(0L);
        Date high = new Date(0L);
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
        List<Order> orders = new ArrayList<>();
        Order order = new Order(0L, new Date(Long.MAX_VALUE), 100D,"shop", null, new ArrayList<>());
        order.getItems().add(new Item(0,"",0D,1,"url","cate1","cate2","cate3",order));
        orders.add(order);
        when(taobaoService.getOrderByUserAndDate(Mockito.any(User.class),Mockito.any(Date.class),Mockito.any(Date.class))).thenReturn(orders);

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
    void getUserStatistics() throws Exception {
        String username = "username";
        User user = new User("name", "password", new Date(0L));
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
        Stats stats = new Stats();
        when(taobaoService.getStats(user)).thenReturn(stats);

        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.get("/stats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(JSON.toJSONString(stats,true),response);
    }

    @Test
    void updateUserCategory() throws Exception {
        String username = "username";
        User user = new User("name", "password", new Date(0L));
        when(taobaoService.getUserByUsername(username)).thenReturn(user);
        when(analyzeService.updateCategory(user)).thenReturn(0);
        MockHttpServletRequestBuilder loginRequest =
                MockMvcRequestBuilders.post("/stats/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username",username);
        String response = mockMvc.perform(loginRequest).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(0,Integer.parseInt(response));
    }
}