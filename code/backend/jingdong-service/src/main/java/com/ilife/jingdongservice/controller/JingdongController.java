package com.ilife.jingdongservice.controller;


import com.ilife.jingdongservice.entity.Order;
import com.ilife.jingdongservice.entity.User;
import com.ilife.jingdongservice.service.CrawlerService;
import com.ilife.jingdongservice.service.JingDongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@Api(value = "JingdongServiceController")
public class JingdongController {
    @Autowired
    JingDongService jingDongService;
    @Autowired
    CrawlerService crawlerService;

    @ApiOperation(notes = "login with qrcode", value = "",httpMethod = "POST")
    @PostMapping(value = "/login/qrcode", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> loginIntoJingdong(@RequestParam String username){
        System.out.println("login : " + username);
        User user = jingDongService.getUserByUsername(username);
        if (user == null) {
            User newUser = new User(username, new Date(0L));
            jingDongService.saveUser(newUser);
        }
        String response =  crawlerService.login(username);
        System.out.println(response);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(notes = "login with qrcode", value = "",httpMethod = "POST")
    @GetMapping(value = "/login/check", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> loginCheck(@RequestParam String username){
        Boolean isLogin =  crawlerService.loginCheck(username);
        return ResponseEntity.ok().body(isLogin);
    }

    @ApiOperation(notes = "update all history order", value = "",httpMethod = "POST")
    @PostMapping(value = "/order/crawl/all", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateOrderAll(@RequestParam String username){
        Integer response =  crawlerService.fetchHistory(username);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(notes = "update history order after date specified", value = "",httpMethod = "POST")
    @PostMapping(value = "/order/crawl/after", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateOrderAfter(@RequestParam String username, @RequestParam Date date){
        System.out.println(date.toString());
        Integer response =  crawlerService.fetchHistoryAfter(username,date);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(notes = "incremental crawl", value = "",httpMethod = "POST")
    @PostMapping(value = "/order/crawl/incremental", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateOrderIncremental(@RequestParam String username){
        User user = jingDongService.getUserByUsername(username);
        Date lastDate = user.getLastUpdateDate() == null ? new Date(0L) : user.getLastUpdateDate();
        Integer response ;
        response =  crawlerService.fetchHistoryAfter(username,lastDate);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(notes = "get user's all order", value = "",httpMethod = "GET")
    @GetMapping(value = "/order/all", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllOrder(@RequestParam String username){
        User user = jingDongService.getUserByUsername(username);
        List<Order> orders = jingDongService.getOrderByUser(user);
        return ResponseEntity.ok().body(orders);
    }
    @ApiOperation(notes = "get user's order between date", value = "",httpMethod = "GET")
    @GetMapping(value = "/order/between", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getOrderBetween(@RequestParam String username, @RequestParam Date low, @RequestParam Date high){
        User user = jingDongService.getUserByUsername(username);
        List<Order> orders = jingDongService.getOrderByUserAndDate(user, low, high);
        return ResponseEntity.ok().body(orders);
    }
    @ApiOperation(notes = "get user's information", value = "", httpMethod = "GET")
    @GetMapping(value = "/user", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUser(@RequestParam String username){
        User user = jingDongService.getUserByUsername(username);
        return ResponseEntity.ok().body(user);
    }
}
