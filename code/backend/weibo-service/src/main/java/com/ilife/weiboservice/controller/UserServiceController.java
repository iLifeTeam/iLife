package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@Api(value = "User Service Controller")
    public class UserServiceController {

        @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info", httpMethod = "GET")
    @GetMapping(path = "/user/getById")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getUserById(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid, HttpServletResponse response) {
        System.out.println("********** getUserByUserId **********");
        return userService.findAllById(uid);
    }

    @ApiOperation(notes = "Get user info by nickname", value = "get user info", httpMethod = "GET")
    @GetMapping(path = "/user/getByName")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getUserByNickname(@ApiParam(name = "nickname", value = "The nickname of a WeiBo user,should be a String") @RequestParam("nickname") String nickname,HttpServletResponse response) {
        System.out.println("********** getUserByNickname **********");
        return userService.findByNickname(nickname);
    }

    @ApiOperation(notes = "Delete one user info specified by userId", value = "delete one user", httpMethod = "GET")
    @GetMapping(path = "/user/delById")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> delUserByUserId(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid,HttpServletResponse response) {
        System.out.println("********** deleteUser **********");
        return userService.deleteById(uid);
    }
}

