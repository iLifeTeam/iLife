package com.ilife.zhihu.controller;


import com.ilife.zhihu.crawller.ZhihuCrawlerServiceClient;
import com.ilife.zhihu.service.ZhihuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Base64;


@RestController
public class ZhihuController {
    private final String CRAWLER_HOSTNAME  = "python-crawller";
    private final int CRAWLLER_PORT = 4001;
    ZhihuCrawlerServiceClient crawlerServiceClient = new ZhihuCrawlerServiceClient(CRAWLER_HOSTNAME, CRAWLLER_PORT);

    @Autowired
    ZhihuService zhihuService;


    void saveImageString(String token, String filename) {
        try {
            File file = new File("./captcha"+ "/" + filename);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if(!file.exists()) {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file);
            byte[] bytes = Base64.getDecoder().decode(token);
            out.write(bytes);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @PostMapping(value = "/login", produces = "application/json")
    String loginIntoZhihu(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(name = "captcha", required = false) String captcha){

        String response = captcha == null ? crawlerServiceClient.login(username,password)
                                          : crawlerServiceClient.login(username,password,captcha);
        if (!response.equals( "success")){
           saveImageString(response,  username + ".gif");
        }
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
