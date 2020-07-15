package com.ilife.weiboservice.service.serviceimpl;

import static java.lang.Long.parseLong;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.entity.User;
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

/**
 * UserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 15, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDao userDao;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User(123L, "david", 100, 200, 300, "", "man", "home", "patient", "kinderGarden", "home");
        try{userDao.save(user);}catch (Exception e1){
            e1.printStackTrace();
        }
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findAllById(Long id)
     */
    @Test
    public void testFindAllById() throws Exception {

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


    }

    /**
     * Method: findByNickname(String nickname)
     */
    @Test
    public void testFindByNickname() throws Exception {
        //pass normal parameters
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
     * Method: deleteById(Long uid)
     */
    @Test
    public void testDeleteById() throws Exception {
//TODO: Test goes here... 
    }


} 
