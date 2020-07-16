package com.ilife.alipayservice.controller;

import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.entity.User;
import com.ilife.alipayservice.service.AlipayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@Api(value = "AplipayServiceController")
public class AlipayController {


    @Autowired
    AlipayService alipayService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest{
        String username;  // its actually phone number
        String password;
        String captcha;
    }

    @ApiOperation(notes = "login with username, password, and optional captcha; not implemented, always return success!", value = "",httpMethod = "POST")
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> loginIntoAlipay(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body("Login Success!");
    }

    @ApiOperation(notes = "get uid's bill between timestamp lower and timestamp upper", value = "",httpMethod = "GET")
    @GetMapping(value = "/bills", produces = "application/json")
    public List<Bill> getBills(@RequestParam Integer uid,
                               @RequestParam Timestamp lower,
                               @RequestParam Timestamp upper){
        return alipayService.getUserBillsBetween(uid, lower, upper);
    }

    @ApiOperation(notes = "get uid's all bill", value = "",httpMethod = "GET")
    @GetMapping(value = "/bills/all", produces = "application/json")
    public List<Bill> getAllBills(@RequestParam Integer uid){
        return alipayService.getUserAllBills(uid);
    }

    @ApiOperation(notes = "get user information with username(actually phone number)", value = "",httpMethod = "GET")
    @GetMapping(value = "/user", produces = "application/json")
    public User getUser(@RequestParam String username){
        return alipayService.getUserWithPhone(username);
    }


}
