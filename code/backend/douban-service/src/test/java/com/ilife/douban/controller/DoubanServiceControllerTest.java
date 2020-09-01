package com.ilife.douban.controller;

import com.ilife.douban.dao.BookDao;
import com.ilife.douban.dao.MovieDao;
import com.ilife.douban.dao.UserDao;
import com.ilife.douban.entity.Book;
import com.ilife.douban.entity.Movie;
import com.ilife.douban.entity.Recommendation;
import com.ilife.douban.entity.User;
import com.ilife.douban.repository.BookRepository;
import com.ilife.douban.repository.MovieRepository;
import com.ilife.douban.repository.RcmdRepository;
import com.ilife.douban.repository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DoubanServiceController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 24, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class DoubanServiceControllerTest {


    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RcmdRepository rcmdRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User("yg", "uk", 111, 222, 333, 444, 555, 666, 777);
        Book book = new Book("yg", "aaaaa", "bbbbb", "4444", 5555, 6666);
        Movie movie = new Movie("yg", "aaaaa", "bbbbb", "ccccc", 5, 6666);
        Recommendation recommendation=new Recommendation("yg","5561","1555",61,"551","1212","331","142","1",1555,"155","15","412","31","12");
        userRepository.save(user);
        bookRepository.save(book);
        movieRepository.save(movie);
        rcmdRepository.save(recommendation);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUserById(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetUserById() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonUser = new JSONObject(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.get("name"),"uk");
    }

    /**
     * Method: deleteById(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestBody Map<String, String> params)
     */
    @Test
    public void testDeleteById() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", "yg");
        MvcResult authResult = mockMvc.perform(post("/douban/delById")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .content(jsonObject.toString())//请求的参数（可多个）
        ).andExpect(status().isOk()).andReturn();
        Assert.assertNull(userRepository.findAllById("yg"));
    }

    /**
     * Method: getBooks(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetBooks() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getBooks")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONArray jsonUser = new JSONArray(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.getJSONObject(0).get("author"),"bbbbb");
    }

    /**
     * Method: getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetMovies() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getMovies")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONArray jsonUser = new JSONArray(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.getJSONObject(0).get("type"),"bbbbb");
    }

    /**
     * Method: getMovieStats(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetMovieStats() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getMovieStats")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("bbbbb","bbbbb");
    }

    /**
     * Method: getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetBookStats() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getMovies")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONArray jsonUser = new JSONArray(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.getJSONObject(0).get("type"),"bbbbb");
    }

    /**
     * Method: getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetRefParameter() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getRcmd")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonUser = new JSONObject(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.get("preAuthor"),"bbbbb");
    }

    /**
     * Method: getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testSaveRcmd() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getStoredRcmd")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonUser = new JSONObject(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.get("author_book"),"1555");
    }

    /**
     * Method: getMovies(@ApiParam(name = "userId", value = "The user ID of a douban user") @RequestParam("userId") String uid)
     */
    @Test
    public void testGetStoredRcmd() throws Exception {
        MvcResult authResult;
        authResult = mockMvc.perform(get("/douban/getStoredRcmd")//使用get方式来调用接口。
                .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
                .param("userId", "yg")//请求的参数（可多个）
        ).andExpect(status().isOk())
                .andReturn();
        JSONObject jsonUser = new JSONObject(authResult.getResponse().getContentAsString());
        Assert.assertEquals(jsonUser.get("author_book"),"1555");
    }
} 
