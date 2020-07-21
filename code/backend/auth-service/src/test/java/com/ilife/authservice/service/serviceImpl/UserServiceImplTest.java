package com.ilife.authservice.service.serviceImpl;

import com.ilife.authservice.dao.UserDao;
import com.ilife.authservice.entity.Users;
import com.ilife.authservice.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * UserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 17, 2020</pre>
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

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
        userDao.updateWbId(2L, 12345L);
        id = userDao.findByAccount("zhihugood").getId();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findById(Long id)
     */
    @Test
    public void testFindById() throws Exception {
        Users user=userService.findById(id);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getAccount(),"zhihugood");
    }

    /**
     * Method: findByNickname(String nickname)
     */
    @Test
    public void testFindByNickname() throws Exception {
        Users user=userService.findByNickname("zhihuKing");
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getAccount(),"zhihugood");
    }

    /**
     * Method: findByAccount(String account)
     */
    @Test
    public void testFindByAccount() throws Exception {
        Users user=userService.findByAccount("zhihugood");
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getNickname(),"zhihuKing");
    }

    /**
     * Method: deleteById(Long id)
     */
    @Test
    public void testDeleteById() throws Exception {
        userService.deleteById(id);
        Users user=userDao.findByAccount("zhihugood");
        Assert.assertNull(user);
    }

    /**
     * Method: save(String nickname, String account, String password, String email)
     */
    @Test
    public void testSave() throws Exception {
        userService.save("zhihuKing1", "zhihugood1", "123456", "git@sjtu.edu.cn");
        Assert.assertNotNull(userDao.findByAccount("zhihugood1"));
    }

    /**
     * Method: updateWyyId(Long id, Long wyyId)
     */
    @Test
    public void testUpdateWyyId() throws Exception {
        ResponseEntity<?> response=userService.updateWyyId(id,123L);
        Users user=userDao.findById(id);
        Assert.assertEquals(response.getBody(),1);
    }

    /**
     * Method: updateWbId(Long id, Long wbId)
     */
    @Test
    public void testUpdateWbId() throws Exception {
        ResponseEntity<?> response=userService.updateWbId(id,123L);
        Users user=userDao.findById(id);
        Assert.assertEquals(response.getBody(),1);
    }

    /**
     * Method: updateZhId(Long id, String zhId)
     */
    @Test
    public void testUpdateZhId() throws Exception {
        ResponseEntity<?> response=userService.updateZhId(id,"asdfg");
        Users user=userDao.findById(id);
        Assert.assertEquals(response.getBody(),1);
    }

    /**
     * Method: auth(String account, String password)
     */
    @Test
    public void testAuth() throws Exception {
        ResponseEntity<?> response=userService.auth("zhihugood","123456");
        Assert.assertEquals(response.getStatusCodeValue(),200);
        response=userService.auth("zhihugood1","123456");
        Assert.assertEquals(response.getStatusCodeValue(),501);
        response=userService.auth("zhihugood","1234561");
        Assert.assertEquals(response.getStatusCodeValue(),502);
    }


} 
