package com.ilife.zhihu.controller;


import com.ilife.zhihu.crawller.ZhihuCrawlerServiceClient;
import com.ilife.zhihu.service.ZhihuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Scanner;


@RestController
public class ZhihuController {

    ZhihuCrawlerServiceClient crawlerServiceClient = new ZhihuCrawlerServiceClient("127.0.0.1", 4001); //todo: hardcoded ip and port

    @Autowired
    ZhihuService zhihuService;

    @PostMapping(value = "/login", produces = "application/json")
    String loginIntoZhihu(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(name = "captcha", required = false) String captcha){
        String response = captcha == null ? crawlerServiceClient.login(username,password)
                                          : crawlerServiceClient.login(username,password,captcha);
        System.out.println(response);
        return response;
    }
    @PostMapping(value = "/updateActivities",produces = "application/json")
    String updateUserActivities(
            @RequestParam("username") String username){
        String response = crawlerServiceClient.getActivities(username);
//        System.out.println(response);
        if(response.equals("not login"))
            return response;
        Integer updated = zhihuService.saveActivitiesFromJsonString(username, response);
        return "";
    }

}
