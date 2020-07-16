package com.ilife.authservice.controller;

import com.ilife.authservice.dao.UserDao;
import com.ilife.authservice.entity.Users;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;

import static java.lang.Long.parseLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserServiceController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 16, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDao userDao;

    private MockMvc mockMvc;

    private Long id;

    @Test
    public void contextLoads() {
    }

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Users user = new Users("zhihuKing", "zhihugood", "123456", "git@sjtu.edu.cn");
        userDao.save(user);
        userDao.updateWbId(2L,12345L);
        id = userDao.findByAccount("zhihugood").getId();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUserById(@ApiParam(name = "userId", value = "The user ID of a iLife user") @RequestParam("userId") Long uid)
     */
    @Test
    public void testGetUserById() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/auth/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", id.toString())//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        System.out.println(authResult.getResponse().getContentAsString());
        JSONObject jsonObject = new JSONObject(authResult.getResponse().getContentAsString());
        String nickname = jsonObject.get("nickname").toString();
        Assert.assertEquals(nickname, "zhihuKing");
        Assert.assertNotNull(jsonObject.get("password"));
        //user not exists
        authResult = mockMvc.perform(get("/auth/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "090909090")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(authResult.getResponse().getContentAsString(), "");
    }

    /**
     * Method: getUserByAccount(@ApiParam(name = "account", value = "The account number of a iLife user") @RequestParam("account") String account)
     */
    @Test
    public void testGetUserByAccount() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/auth/getByAccount")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("account", "zhihugood")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        System.out.println(authResult.getResponse().getContentAsString());
        JSONObject jsonObject = new JSONObject(authResult.getResponse().getContentAsString());
        String nickname = jsonObject.get("nickname").toString();
        Assert.assertEquals(nickname, "zhihuKing");
        Assert.assertNotNull(jsonObject.get("password"));
        //user not exists
        authResult = mockMvc.perform(get("/auth/getByAccount")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("account", "usernotexist")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(authResult.getResponse().getContentAsString(), "");
    }

    /**
     * Method: getUserByNickname(@ApiParam(name = "nickname", value = "The user nickname of a iLife user") @RequestParam("nickname") String nickname)
     */
    @Test
    public void testGetUserByNickname() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/auth/getByNickname")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("nickname", "zhihuking")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        System.out.println(authResult.getResponse().getContentAsString());
        JSONObject jsonObject = new JSONObject(authResult.getResponse().getContentAsString());
        String account = jsonObject.get("account").toString();
        Assert.assertEquals(account, "zhihugood");
        Assert.assertNotNull(jsonObject.get("password"));
        //user not exists
        authResult = mockMvc.perform(get("/auth/getByNickname")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("nickname", "usernotexist")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(authResult.getResponse().getContentAsString(), "");
    }

    /**
     * Method: deleteById(@ApiParam(name = "userId", value = "The user ID of a iLife user") @RequestBody Map<String, String> params)
     */
    @Test
    public void testDeleteById() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", "90909090");
        mockMvc.perform(post("/auth/delById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .content(jsonObject.toString())//请求的参数（可多个）
        ).andExpect(status().is(501))
                .andReturn();
        jsonObject.remove("userId");
        jsonObject.put("userId", id.toString());
        mockMvc.perform(post("/auth/delById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .content(jsonObject.toString())//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
    }

    /**
     * Method: register(@ApiIgnore @RequestBody Map<String, String> params)
     */
    @Test
    public void testRegister() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: auth(@ApiIgnore @RequestBody Map<String, String> params)
     */
    @Test
    public void testAuth() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateWyy(@ApiIgnore @RequestBody Map<String, String> params)
     */
    @Test
    public void testUpdateWyy() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateWb(@ApiIgnore @RequestBody Map<String, String> params)
     */
    @Test
    public void testUpdateWb() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", id);
        jsonObject.put("wbId", 123456789L);
        mockMvc.perform(post("/auth/updateWbId")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .content(jsonObject.toString())//请求的参数（可多个）
        ).andExpect(status().isOk());
        MvcResult authResult;
        authResult = mockMvc.perform(get("/auth/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", id.toString())//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject2 = new JSONObject(authResult.getResponse().getContentAsString());
        System.out.println(jsonObject2.toString());
        long wbId = parseLong(jsonObject2.get("weibid").toString());
        Assert.assertEquals(wbId,123456789L);
    }

    /**
     * Method: updateZh(@ApiIgnore @RequestBody Map<String, String> params)
     */
    @Test
    public void testUpdateZh() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", id);
        jsonObject.put("zhId", 123456789L);
        mockMvc.perform(post("/auth/updateZhId")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .content(jsonObject.toString())//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        MvcResult authResult;
        authResult = mockMvc.perform(get("/auth/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", id.toString())//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject2 = new JSONObject(authResult.getResponse().getContentAsString());
        String zhid = jsonObject2.get("zhid").toString();
        Assert.assertEquals(zhid,"123456");
    }


} 
