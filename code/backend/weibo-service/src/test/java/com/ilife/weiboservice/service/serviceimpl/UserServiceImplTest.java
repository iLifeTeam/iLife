package com.ilife.weiboservice.service.serviceimpl;


import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.entity.User;
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

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDao userDao;

    @Before
    public void before() throws Exception {
        User user = new User(123L, "david", 100, 200, 300, "", "man", "home", "patient", "kinderGarden", "home");
        try {
            userDao.save(user);
        } catch (Exception e1) {
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
        //pass normal parameters
        User user = userDao.findAllById(123L);
        Assert.assertEquals((long) user.getFollowers(), 100L);
        //nickname not exists
        user = userDao.findAllById(90909090L);
        Assert.assertNull(user);
    }

    /**
     * Method: findByNickname(String nickname)
     */
    @Test
    public void testFindByNickname() throws Exception {
        //pass normal parameters
        User user = userDao.findByNickname("david");
        Assert.assertEquals((long) user.getFollowers(), 100L);
        //nickname not exists
        user = userDao.findByNickname("davidIsDead12345");
        Assert.assertNull(user);
    }

    /**
     * Method: deleteById(Long uid)
     */
    @Test
    public void testDeleteById() throws Exception {
        userDao.deleteById(123L);
        User user = userDao.findByNickname("david");
        Assert.assertNull(user);
    }


} 
