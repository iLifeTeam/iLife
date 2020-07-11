package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
//@Api(tags={"User Service Controller"})
public class UserServiceController {


    @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info",httpMethod = "GET")
    @RequestMapping(path="/user/getUser")
    public User getUser(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid){
        System.out.println("*****getWeibo*****");
        return userService.findAllById(uid);
    }
}
