package com.ilife.weiboservice.controller;

import com.alibaba.fastjson.JSON;
import com.ilife.weiboservice.dao.WeiboDao;
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
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        for(int i = 0; i<10; ++i){
            Weibo weibo=new Weibo("sE2Rhe7epn"+ i,123,"today is a good day","home",100*i,200*i,300*i,new Date());
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
        MvcResult authResult;
        authResult = mockMvc.perform(get("/weibo/getWeibos")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "123")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONArray jsonArray = new JSONArray(authResult.getResponse().getContentAsString());
        JSONObject jsonObject=jsonArray.getJSONObject(3);
        Assert.assertEquals(jsonObject.get("retweet_num"),300);
        Assert.assertEquals(jsonArray.length(),10);

    }

    /**
     * Method: getWeibo(@ApiParam(name = "Id", value = "The ID of a WeiBo,should be a String") @RequestParam("Id") String id)
     */
    @Test
    public void testGetWeibo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: crawlWeibo(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid)
     */
    @Test
    public void testCrawlWeibo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Integer uid)
     */
    @Test
    public void testDeleteWeibos() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteWeibo(@ApiParam(name = "Id", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("Id") Integer id)
     */
    @Test
    public void testDeleteWeibo() throws Exception {
//TODO: Test goes here... 
    }


} 
