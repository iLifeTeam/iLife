package com.ilife.weiboservice.service.serviceimpl;

import static org.junit.Assert.*;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.User;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * WeiboServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 15, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeiboServiceImplTest {
    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private WeiboDao weiboDao;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User(123L, "david", 100, 200, 300, "", "man", "home", "patient", "kinderGarden", "home");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findAllByUid(Integer uid)
     */
    @Test
    public void testFindAllByUid() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteByUid(Integer uid)
     */
    @Test
    public void testDeleteByUid() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findById(String id)
     */
    @Test
    public void testFindById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: crawlWeibo(Long uid)
     */
    @Test
    public void testCrawlWeibo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteById(Integer id)
     */
    @Test
    public void testDeleteById() throws Exception {
//TODO: Test goes here... 
    }


} 
