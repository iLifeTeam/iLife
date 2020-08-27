package com.ilife.weiboservice.controller;

import static java.lang.Long.parseLong;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.entity.User;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * UserServiceController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 15, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User(123L, "david", 100, 200, 300, "", "man", "home", "patient", "kinderGarden", "home","avatar");
        userDao.save(user);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUserById(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid)
     */
    @Test
    public void testGetUserById() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/user/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "123")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(authResult.getResponse().getContentAsString());
        String nickname = jsonObject.get("nickname").toString();
        Assert.assertEquals(nickname, "david");
        Assert.assertNotNull(jsonObject.get("location"));
        //user not exists
        authResult = mockMvc.perform(get("/user/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "090909090")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(authResult.getResponse().getContentAsString(), "");
    }

    /**
     * Method: getUserByNickname(@ApiParam(name = "nickname", value = "The nickname of a WeiBo user,should be a String") @RequestParam("nickname") String nickname)
     */
    @Test
    public void testGetUserByNickname() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/user/getByName")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("nickname", "david")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(authResult.getResponse().getContentAsString());
        Long id = parseLong(jsonObject.get("id").toString());
        System.out.println(id);
        Assert.assertEquals((long) id, 123L);
        Assert.assertNotNull(jsonObject.get("location"));
        //user not exists
        authResult = mockMvc.perform(get("/user/getByName")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("nickname", "davidUndefined")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(authResult.getResponse().getContentAsString(), "");
    }

    /**
     * Method: delUserByUserId(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid)
     */
    @Test
    public void testDelUserByUserId() throws Exception {
        //user not exists
        mockMvc.perform(get("/user/delById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "9090909090")//请求的参数（可多个）
        ).andExpect(status().is(501))
                .andReturn();
        //successfully delete user
        mockMvc.perform(get("/user/delById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "123")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
    }


} 
