package com.ilife.taobaoservice.controller;


import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.service.CrawlerService;
import com.ilife.taobaoservice.service.TaobaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@Api(value = "AplipayServiceController")
public class TaobaoController {
    @Autowired
    TaobaoService taobaoService;
    @Autowired
    CrawlerService crawlerService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest{
        String username;  // its actually phone number
        String password;
    }
    @ApiOperation(notes = "login with username, password", value = "",httpMethod = "POST")
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> loginIntoTaobao(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.username;
        String password = loginRequest.password;
        User user = new User(username,password, new Date(0L));
        taobaoService.saveUser(user);
        String response =  crawlerService.login(username,password);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(notes = "update all history order", value = "",httpMethod = "POST")
    @PostMapping(value = "/order/crawl/all", produces = "application/json")
    public ResponseEntity<?> updateOrderAll(@RequestParam String username){
        Integer response =  crawlerService.fetchHistory(username);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(notes = "update history order after date specified", value = "",httpMethod = "POST")
    @PostMapping(value = "/order/crawl/after", produces = "application/json")
    public ResponseEntity<?> updateOrderAfter(@RequestParam String username, @RequestParam Date date){
        System.out.println(date.toString());
        Integer response =  crawlerService.fetchHistoryAfter(username,date);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(notes = "incremental crawl", value = "",httpMethod = "POST")
    @PostMapping(value = "/order/crawl/incremental", produces = "application/json")
    public ResponseEntity<?> updateOrderIncremental(@RequestParam String username){
        User user = taobaoService.getUserByUsername(username);
        Date lastDate = user.getLastUpdateDate() == null ? new Date(0L) : user.getLastUpdateDate();
        Integer response ;
        response =  crawlerService.fetchHistoryAfter(username,lastDate);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(notes = "get user's all order", value = "",httpMethod = "GET")
    @GetMapping(value = "/order/all", produces = "application/json")
    public ResponseEntity<?> getAllOrder(@RequestParam String username){
        User user = taobaoService.getUserByUsername(username);
        List<Order> orders = taobaoService.getOrderByUser(user);
        return ResponseEntity.ok().body(orders);
    }
    @ApiOperation(notes = "get user's order between date", value = "",httpMethod = "GET")
    @GetMapping(value = "/order/between", produces = "application/json")
    public ResponseEntity<?> getOrderBetween(@RequestParam String username, @RequestParam Date low, @RequestParam Date high){
        User user = taobaoService.getUserByUsername(username);
        List<Order> orders = taobaoService.getOrderByUserAndDate(user, low, high);
        return ResponseEntity.ok().body(orders);
    }

}
