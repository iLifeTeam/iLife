package com.ilife.weiboservice.service.serviceimpl;


import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.entity.Weibo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private WeiboDao weiboDao;

    @Autowired
    private UserDao userDao;

    @Before
    public void before() throws Exception {
        User user = new User(123L, "david", 100, 200, 300, "", "man", "home", "patient", "kinderGarden", "home","shit");
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
     * Method: findAllByUid(Integer uid)
     */
    @Test
    public void testFindAllByUid() throws Exception {
        //valid uid
        List<Weibo> weiboList = weiboDao.findAllByUid(123L);
        Assert.assertEquals(weiboList.size(),10);
        Assert.assertEquals((long)weiboList.get(3).getUp_num(),900L);
        //invalid uid
        weiboList = weiboDao.findAllByUid(90909090L);
        Assert.assertEquals(weiboList.size(),0);
    }

    /**
     * Method: deleteByUid(Integer uid)
     */
    @Test
    public void testDeleteByUid() throws Exception {
        //valid uid
        List<Weibo> weiboList = weiboDao.findAllByUid(123L);
        Assert.assertEquals(weiboList.size(),10);
        weiboDao.deleteByUid(123L);
        weiboList = weiboDao.findAllByUid(123L);
        Assert.assertEquals(weiboList.size(),0);
    }

    /**
     * Method: findById(String id)
     */
    @Test
    public void testFindById() throws Exception {
        //valid weiboId
        Weibo weibo = weiboDao.findById("sE2Rhe7epn2");
        Assert.assertNotNull(weibo);
        Assert.assertEquals((long)weibo.getRetweet_num(),200);
        //invalid weiboId
        weibo = weiboDao.findById("sE2Rhe7epn10");
        Assert.assertNull(weibo);
    }

    /**
     * Method: crawlWeibo(Long uid)
     */
    @Test
    public void testCrawlWeibo() throws Exception {

    }

    /**
     * Method: deleteById(Integer id)
     */
    @Test
    public void testDeleteById() throws Exception {
        weiboDao.deleteById("sE2Rhe7epn2");
        List<Weibo> weiboList = weiboDao.findAllByUid(123L);
        Assert.assertEquals(weiboList.size(),9);
    }


} 
