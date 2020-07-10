package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.UserService;
import com.ilife.weiboservice.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class UserServiceController {

//    @Autowired
//    DiscoveryClient discoveryClient;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/user/getUser")
    public User getBook(@RequestParam("userId") Integer uid){
        System.out.println("*****getWeibo*****");
        return userService.findAllById(uid);
    }
}
