package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping(path="/user/getById")
    public User getUserById(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid){
        System.out.println("********** getUserByUserId **********");
        return userService.findAllById(uid);
    }
    @ApiOperation(notes = "Get user info by nickname", value = "get user info",httpMethod = "GET")
    @RequestMapping(path="/user/getByName")
    public User getUserByNickname(@ApiParam(name = "nickname", value = "The nickname of a WeiBo user,should be a String") @RequestParam("nickname") String nickname){
        System.out.println("********** getUserByNickname **********");
        return userService.findByNickname(nickname);
    }

    @ApiOperation(notes = "Delete one user info specified by userId", value = "delete one user",httpMethod = "GET")
    @RequestMapping(path="/user/delById")
    public ResponseEntity<?> getUserByNickname(@ApiParam(name = "userId",  value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid){
        System.out.println("********** deleteUser **********");
        return userService.deleteById(uid);
    }
}

