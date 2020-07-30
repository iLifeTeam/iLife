package com.ilife.taobaoservice.controller;


import com.ilife.taobaoservice.entity.Order;
import com.ilife.taobaoservice.entity.Stats;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.service.AnalyzeService;
import com.ilife.taobaoservice.service.CrawlerService;
import com.ilife.taobaoservice.service.TaobaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@Api(value = "TaobaoServiceController")
public class TaobaoController {
    @Autowired
    TaobaoService taobaoService;
    @Autowired
    CrawlerService crawlerService;
    @Autowired
    AnalyzeService analyzeService;

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    @ApiOperation(notes = "login with phone", value = "",httpMethod = "POST")
    @PostMapping(value = "/login/sms/fetch", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> fetchLoginSms(@RequestParam String phone){
        User user = taobaoService.getUserByUsername(phone);
        System.out.println(phone);
        if (user == null) {
            user = new User(phone, "", new Date(0L));
            taobaoService.saveUser(user);
        }
        String response =  crawlerService.fetchSms(phone);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(notes = "login with phone", value = "",httpMethod = "POST")
    @PostMapping(value = "/login/sms", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> loginWithSmsCode(@RequestParam String phone, @RequestParam String smsCode){
        User user = taobaoService.getUserByUsername(phone);
        if (user == null) {
            user = new User(phone, "", new Date(0L));
            taobaoService.saveUser(user);
        }
        String response =  crawlerService.loginWithSms(phone,smsCode);
        return ResponseEntity.ok().body(response);
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
        User user = taobaoService.getUserByUsername(username);
        Date lastDate = user.getLastUpdateDate() == null ? new Date(0L) : user.getLastUpdateDate();
        Integer response ;
        response =  crawlerService.fetchHistoryAfter(username,lastDate);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(notes = "get user's all order", value = "",httpMethod = "GET")
    @GetMapping(value = "/order/all", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllOrder(@RequestParam String username){
        User user = taobaoService.getUserByUsername(username);
        List<Order> orders = taobaoService.getOrderByUser(user);
        return ResponseEntity.ok().body(orders);
    }
    @ApiOperation(notes = "get user's order between date", value = "",httpMethod = "GET")
    @GetMapping(value = "/order/between", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getOrderBetween(@RequestParam String username, @RequestParam Date low, @RequestParam Date high){
        User user = taobaoService.getUserByUsername(username);
        List<Order> orders = taobaoService.getOrderByUserAndDate(user, low, high);
        return ResponseEntity.ok().body(orders);
    }

    @ApiOperation(notes = "get user's statistics", value = "", httpMethod = "GET")
    @GetMapping(value = "/stats", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserStatistics(@RequestParam String username){
        User user = taobaoService.getUserByUsername(username);
        Stats stats = taobaoService.getStats(user);
        return ResponseEntity.ok().body(stats);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdateCate extends Thread {
        private User user;
        @Override
        public void run() {
            analyzeService.updateCategory(user);
        }
    }

    @ApiOperation(notes = "get user's statistics", value = "", httpMethod = "GET")
    @PostMapping(value = "/stats/category/update", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateUserCategory(@RequestParam String username){
        User user = taobaoService.getUserByUsername(username);
        executor.execute(new UpdateCate(user));
//        analyzeService.updateCategory(user);
        return ResponseEntity.ok().body();
    }

}
