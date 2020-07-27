package com.ilife.zhihu.controller;


import com.ilife.zhihu.crawller.ZhihuCrawlerServiceClient;
import com.ilife.zhihu.entity.*;
import com.ilife.zhihu.service.ZhihuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;



@RestController
@Api(value = "ZhihuServiceController")
@CrossOrigin(origins = "*")
public class ZhihuController {
    //    private final String CRAWLER_HOSTNAME  = "localhost";
    private final String CRAWLER_HOSTNAME = "zhihu-crawller";
    private final int CRAWLLER_PORT = 4001;
    ZhihuCrawlerServiceClient crawlerServiceClient;

    @Autowired
    ZhihuService zhihuService;

    public ZhihuController() {
        this.crawlerServiceClient = new ZhihuCrawlerServiceClient(CRAWLER_HOSTNAME, CRAWLLER_PORT);
    }

    public void setCrawlerServiceClient(ZhihuCrawlerServiceClient crawlerServiceClient) {
        this.crawlerServiceClient = crawlerServiceClient;
    }

    @ApiOperation(notes = "login with username, password, and optional captcha", value = "", httpMethod = "POST")
    @PostMapping(value = "/login", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> loginIntoZhihu(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.username;
        String password = loginRequest.password;
        if (password == null || email == null) {
            return ResponseEntity.badRequest().body("Need Password!");
        }
        String captcha = loginRequest.captcha;
        String response = login(email, password, captcha);
        System.out.println("login response: " + response);
        if (!response.equals("success")) {
            saveImage(response, email + ".gif");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            String userInfoJson = crawlerServiceClient.getUserInfo(email);
            System.out.println("user info json \n " + userInfoJson);
            User user = zhihuService.saveUserFromJsonString(email, userInfoJson);
            return ResponseEntity.ok().body("Login successfully!");
        }
    }

    @ApiOperation(notes = "update user activities", value = "", httpMethod = "POST")
    @PostMapping(value = "/updateActivities", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateUserActivities(
            @RequestParam("username") String username
    ) {
        String response = crawlerServiceClient.getActivities(username);
        if (response.equals("not login"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not login!");
        User user = zhihuService.getUserWithEmail(username);
        zhihuService.saveActivitiesFromJsonString(user, response);
        return ResponseEntity.ok().body("Update successfully!");
    }

    @ApiOperation(notes = "GET user information", value = "", httpMethod = "GET")
    @GetMapping(value = "/user", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getUser(
            @RequestParam("username") String username) {
        return zhihuService.getUserWithEmail(username);
    }

    @ApiOperation(notes = "GET user activities", value = "", httpMethod = "GET")
    @GetMapping(value = "/activity/all", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Activity> getActivity(
            @RequestParam("username") String username) {
        return zhihuService.getUserActivity(username);
    }

    @ApiOperation(notes = "GET article", value = "", httpMethod = "GET")
    @GetMapping(value = "/article", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Article getArticle(
            @RequestParam("id") String id) {
        return zhihuService.getArticleById(id);
    }

    @ApiOperation(notes = "GET question", value = "", httpMethod = "GET")
    @GetMapping(value = "/question", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Question getQuestion(
            @RequestParam("id") String id) {
        return zhihuService.getQuestionById(id);
    }

    @ApiOperation(notes = "GET answer", value = "", httpMethod = "GET")
    @GetMapping(value = "/answer", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Answer getAnswer(
            @RequestParam("id") String id) {
        return zhihuService.getAnswerById(id);
    }

    public void saveImage(String image, String filename) {
        try {
            File file = new File("./captcha" + "/" + filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file);
            byte[] bytes = Base64.getDecoder().decode(image);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private String login(String email, String password, String captcha) {
        return captcha == null ?
                crawlerServiceClient.login(email, password)
                : crawlerServiceClient.login(email, password, captcha);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        String username; /* its actually email */
        String password;
        String captcha;
    }
}
