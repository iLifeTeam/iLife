package com.ilife.weiboservice.controller;
import static org.junit.Assert.*;

import com.ilife.weiboservice.entity.User;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* UserServiceController Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 15, 2020</pre> 
* @version 1.0 
*/
public class UserServiceControllerTest {
    private UserServiceController userServiceController;
@Before
public void before() throws Exception {
    userServiceController=new UserServiceController();
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getUserById(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid) 
* 
*/ 
@Test
public void testGetUserById() throws Exception { 
//TODO: Test goes here...
//    User user=userServiceController.getUserById((long) 1234123);
} 

/** 
* 
* Method: getUserByNickname(@ApiParam(name = "nickname", value = "The nickname of a WeiBo user,should be a String") @RequestParam("nickname") String nickname) 
* 
*/ 
@Test
public void testGetUserByNicknameNickname() throws Exception { 
//TODO: Test goes here...

} 

/** 
* 
* Method: getUserByNickname(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid) 
* 
*/ 
@Test
public void testGetUserByNicknameUid() throws Exception { 
//TODO: Test goes here... 
} 


} 
