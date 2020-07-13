package com.ilife.zhihu.controller;


import com.alibaba.fastjson.JSON;
import com.ilife.zhihu.crawller.ZhihuCrawlerServiceClient;
import com.ilife.zhihu.entity.Answer;
import com.ilife.zhihu.entity.Article;
import com.ilife.zhihu.entity.Question;
import com.ilife.zhihu.entity.User;
import com.ilife.zhihu.service.ZhihuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.*;
import java.util.Base64;

@RestController
@Api(value = "ZhihuServiceController")
public class ZhihuController {

    private final String CRAWLER_HOSTNAME  = "python-crallwer";
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


    @ApiOperation(notes = "login with username, password, and optional captcha", value = "",httpMethod = "POST")
    @PostMapping(value = "/login", produces = "application/json")
    String loginIntoZhihu(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(name = "captcha", required = false) String captcha){

        String response = captcha == null ? crawlerServiceClient.login(username,password)
                                          : crawlerServiceClient.login(username,password,captcha);
        if (!response.equals( "success")){
           saveImageString(response,  username + ".gif");
        }else {
            String userInfoJson = crawlerServiceClient.getUserInfo(username);
            zhihuService.saveUserFromJsonString(username, userInfoJson);
        }
        System.out.println(response);
        return response;
    }


    @ApiOperation(notes = "update user activities", value = "",httpMethod = "POST")
    @PostMapping(value = "/updateActivities",produces = "application/json")
    String updateUserActivities(
            @RequestParam("username") String username
    ){
        String response = crawlerServiceClient.getActivities(username);
//        System.out.println(response);
        if(response.equals("not login"))
            return response;
        User user = zhihuService.getUserWithEmail(username);
        zhihuService.saveActivitiesFromJsonString(user, response);
        return "";
    }


    @ApiOperation(notes = "GET user information", value = "",httpMethod = "GET")
    @GetMapping(value = "/user",produces = "application/json")
    String getUser(
            @RequestParam("username") String username){
        String response = crawlerServiceClient.getUserInfo(username);
        if(response.equals("not login"))
            return response;
        return JSON.toJSONString(zhihuService.saveUserFromJsonString(username, response)) ;
    }

    @ApiOperation(notes = "GET user activities", value = "",httpMethod = "GET")
    @GetMapping(value = "/activity/all",produces = "application/json")
    String getActivity(
            @RequestParam("username") String username){
        return zhihuService.getUserActivityJson(username);
    }

    @ApiOperation(notes = "GET article", value = "",httpMethod = "GET")
    @GetMapping(value = "/article",produces = "application/json")
    @Transactional
    String getArticle(
            @RequestParam("id") Integer id){
        return zhihuService.getArticleJsonById(id);
    }

    @ApiOperation(notes = "GET question", value = "",httpMethod = "GET")
    @GetMapping(value = "/question",produces = "application/json")
    @Transactional
    String getActivity(
            @RequestParam("id") Integer id){
        return zhihuService.getQuestionJsonById(id);
    }
    @ApiOperation(notes = "GET user activities", value = "",httpMethod = "GET")
    @GetMapping(value = "/answer",produces = "application/json")
    String getAnswer(
            @RequestParam("id") Integer id){
        return zhihuService.getAnswerJsonById(id);
    }


}
