package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.entity.Weibo;
import org.json.JSONArray;
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

import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WeiboServiceController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 15, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeiboServiceControllerTest {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private WeiboDao weiboDao;

    @Autowired
    private UserDao userDao;

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User(123L, "david", 100, 200, 300, "", "man", "home", "patient", "kinderGarden", "home");
        userDao.save(user);
        for (int i = 0; i < 10; ++i) {
            Weibo weibo = new Weibo("sE2Rhe7epn" + i, 123L, "today is a good day", "home", 100 * i, 200 * i, 300 * i, new Timestamp(new Date().getTime()));
            weiboDao.save(weibo);
        }
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Integer uid)
     */
    @Test
    public void testGetWeibos() throws Exception {
        //valid userId
        MvcResult authResult;
        authResult = mockMvc.perform(get("/weibo/getWeibos")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "123")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONArray jsonArray = new JSONArray(authResult.getResponse().getContentAsString());
        JSONObject jsonObject = jsonArray.getJSONObject(3);
        Assert.assertEquals(jsonObject.get("retweet_num"), 300);
        Assert.assertEquals(jsonArray.length(), 10);
        //invalid userId
        authResult = mockMvc.perform(get("/weibo/getWeibos")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "090909090")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        jsonArray = new JSONArray(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonArray.length(), 0);
    }

    /**
     * Method: getWeibo(@ApiParam(name = "Id", value = "The ID of a WeiBo,should be a String") @RequestParam("Id") String id)
     */
    @Test
    public void testGetWeibo() throws Exception {
        //valid weiboId
        MvcResult authResult;
        authResult = mockMvc.perform(get("/weibo/getWeibo")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("Id", "sE2Rhe7epn3")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonWeibo = new JSONObject(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonWeibo.get("retweet_num"), 300);
        //invalid weiboId
        authResult = mockMvc.perform(get("/weibo/getWeibo")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("Id", "sE2Rhe7epn10")//请求的参数（可多个）
        ).andExpect(status().isOk()).andReturn();
        Assert.assertEquals(authResult.getResponse().getContentAsString(), "");

    }

    /**
     * Method: crawlWeibo(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid)
     */
    @Test
    public void testCrawlWeibo() throws Exception {
//        mockMvc.perform(get("/weibo/crawlWeibo")//使用get方式来调用接口。
//                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
//                .param("userId", "")//请求的参数（可多个）
//        ).andExpect(status().isOk());
    }

    /**
     * Method: deleteWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid)
     */
    @Test
    public void testDeleteWeibos() throws Exception {
        //invalid userId
        MvcResult authResult;
        mockMvc.perform(get("/weibo/deleteWeibos")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "09090909")//请求的参数（可多个）
        ).andExpect(status().is(501));
        //successfully delete
        mockMvc.perform(get("/weibo/deleteWeibos")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "123")//请求的参数（可多个）
        ).andExpect(status().isOk());
    }

    /**
     * Method: deleteWeibo(@ApiParam(name = "Id", value = "The ID of a WeiBo,should be a String") @RequestParam("Id") String id)
     */
    @Test
    public void testDeleteWeibo() throws Exception {
        //invalid userId
        MvcResult authResult;
        mockMvc.perform(get("/weibo/deleteWeibo")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("Id", "09090909")//请求的参数（可多个）
        ).andExpect(status().is(501));
        //successfully delete
        mockMvc.perform(get("/weibo/deleteWeibo")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("Id", "sE2Rhe7epn2")//请求的参数（可多个）
        ).andExpect(status().isOk());
    }


} 
