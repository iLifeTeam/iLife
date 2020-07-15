package com.ilife.weiboservice.service.serviceimpl;

import static java.lang.Long.parseLong;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
* @since <pre>Jul 15, 2020</pre> 
* @version 1.0 
*/
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Test
    public void contextLoads() {}
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

@Before
public void before() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    User user=new User(123L,"david",100,200,300,"","man","home","patient","kinderGarden","home");
    
}

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findAllById(Long id) 
* 
*/ 
@Test
public void testFindAllById() throws Exception {

//TODO: Test goes here... 
} 

/** 
* 
* Method: findByNickname(String nickname) 
* 
*/ 
@Test
public void testFindByNickname() throws Exception {
    MvcResult authResult;
    authResult = mockMvc.perform(get("/user/getByName")//使用get方式来调用接口。
            .contentType(MediaType.APPLICATION_JSON_VALUE)//请求参数的类型
            .param("nickname", "土味街拍大赏")//请求的参数（可多个）
    ).andExpect(status().isOk())
            .andReturn();
    //获取数据
    JSONObject jsonObject =new  JSONObject(authResult.getResponse().getContentAsString());
    Long id = parseLong(jsonObject.get("id").toString());
    System.out.println(id);
//    //获取第一个Array中的值,判断查询到的结果。
//    JSONObject jsonObject_data = null;
//    if(jsonArrayData.length()>0){
//        jsonObject_data = (JSONObject) jsonArrayData.get(0);
//    }
//    //加断言，判断属性值的问题。
//    Assert.assertNotNull(jsonObject.get("error_code"));
     Assert.assertEquals((long)id,5406347304L);
//    Assert.assertNotNull(jsonObject.get("error_msg"));
//    Assert.assertEquals(jsonObject.get("error_msg"),"操作成功");
//    Assert.assertNotNull(jsonObject.get("data"));
//    Assert.assertNotNull(jsonObject_data);
//    Assert.assertEquals(jsonObject_data.get("equipmentty"),1);
//    Assert.assertEquals(jsonObject_data.get("equipmenttypename"),"xxxxx");

}

/** 
* 
* Method: deleteById(Long uid) 
* 
*/ 
@Test
public void testDeleteById() throws Exception { 
//TODO: Test goes here... 
} 


} 
