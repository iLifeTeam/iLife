package com.ilife.authservice.service.serviceImpl;

import com.ilife.authservice.dao.UserDao;
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
    private UserDao userDao;

    private MockMvc mockMvc;

    private Long id;

    @Test
    public void contextLoads() {

    }

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findById(Long id)
     */
    @Test
    public void testFindById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findByNickname(String nickname)
     */
    @Test
    public void testFindByNickname() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findByAccount(String account)
     */
    @Test
    public void testFindByAccount() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteById(Long id)
     */
    @Test
    public void testDeleteById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: save(String nickname, String account, String password, String email)
     */
    @Test
    public void testSave() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateWyyId(Long id, Long wyyId)
     */
    @Test
    public void testUpdateWyyId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateWbId(Long id, Long wbId)
     */
    @Test
    public void testUpdateWbId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateZhId(Long id, String zhId)
     */
    @Test
    public void testUpdateZhId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: auth(String account, String password)
     */
    @Test
    public void testAuth() throws Exception {
//TODO: Test goes here... 
    }


} 
