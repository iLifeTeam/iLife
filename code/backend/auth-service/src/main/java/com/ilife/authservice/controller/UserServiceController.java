package com.ilife.authservice.controller;


import com.ilife.authservice.entity.Users;
import com.ilife.authservice.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

import static java.lang.Long.parseLong;


@CrossOrigin(origins = "*")
@RestController

@Api(tags = {"User Service Controller"}, description = "Everything about auth & CRUD of iLife User")
public class UserServiceController {


    @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info by id", httpMethod = "GET")
    @RequestMapping(path = "/auth/getById")
    public Users getUserById(@ApiParam(name = "userId", value = "The user ID of a iLife user") @RequestParam("userId") Long uid) {
        System.out.println("********** getUserById **********");
        return userService.findById(uid);
    }

    @ApiOperation(notes = "Get user info by account", value = "get user info by account", httpMethod = "GET")
    @RequestMapping(path = "/auth/getByAccount")
    public Users getUserByAccount(@ApiParam(name = "account", value = "The account number of a iLife user") @RequestParam("account") String account) {
        System.out.println("********** getUserByAccount **********");
        return userService.findByAccount(account);
    }

    @ApiOperation(notes = "Get user info by Nickname", value = "get user info by nickname", httpMethod = "GET")
    @RequestMapping(path = "/auth/getByNickname")
    public Users getUserByNickname(@ApiParam(name = "nickname", value = "The user nickname of a iLife user") @RequestParam("nickname") String nickname) {
        System.out.println("********** getUserByNickname **********");
        return userService.findByNickname(nickname);
    }
    @ApiResponses({
            @ApiResponse(code = 501, message = "account or nickname already exists"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "the nickname of the iLife user"),
    }
    )
    @ApiOperation(notes = "Delete one ilife user By user Id", value = "delete one user", httpMethod = "POST")
    @RequestMapping(path = "/auth/delById")
    public ResponseEntity<?> deleteById(@ApiParam(name = "userId", value = "The user ID of a iLife user") @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        System.out.println("********** deleteById **********");
        return userService.deleteById(id);
    }

    @ApiOperation(notes = "Register one iLife user by giving nickname,account.password and email", value = "User register", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 500, message = "account already exists"),
            @ApiResponse(code = 501, message = "nickname already exists")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "the nickname of the iLife user"),
            @ApiImplicitParam(name = "account", value = "the account of the iLife user"),
            @ApiImplicitParam(name = "password", value = "the password of the iLife user"),
            @ApiImplicitParam(name = "email", value = "the email of the iLife user")
    }
    )
    @RequestMapping(path = "/auth/register")
    public ResponseEntity<?> register(@ApiIgnore @RequestBody Map<String, String> params) {
        String nickname = params.get("nickname");
        String account = params.get("account");
        String password = params.get("password");
        String email = params.get("email");
        System.out.println("********** register **********");
        return userService.save(nickname, account, password, email);
    }


    @ApiOperation(notes = "Auth one iLife user by giving account.password", value = "User log in", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 501, message = "user not exists"),
            @ApiResponse(code = 502, message = "account and password not match"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "the account of the iLife user"),
            @ApiImplicitParam(name = "password", value = "the password of the iLife user"),
    }
    )
    @RequestMapping(path = "/auth/auth")
    public ResponseEntity<?> auth(@ApiIgnore @RequestBody Map<String, String> params) {
        String account = params.get("account");
        String password = params.get("password");
        System.out.println("********** auth **********");
        return userService.auth(account, password);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "wyyId", value = "the WangYiYun ID of the iLife user"),
            @ApiImplicitParam(name = "userId", value = "the user ID of the iLife user"),
    }
    )
    @ApiOperation(notes = "update user's WangYiYun ID，return the number of affected rows", value = "update wyy ID", httpMethod = "POST")
    @RequestMapping(path = "/auth/updateWyyId")
    public ResponseEntity<?> updateWyy(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        Long wyyId = parseLong(params.get("wyyId"));
        System.out.println("********** updateWyyId **********");
        return userService.updateWyyId(id, wyyId);
    }

    @ApiOperation(notes = "update user's Weibo ID,return the number of affected rows", value = "update Weibo ID", httpMethod = "POST")
    @RequestMapping(path = "/auth/updateWbId")
    public ResponseEntity<?> updateWb(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        Long wbId = parseLong(params.get("wbId"));
        System.out.println("********** updateWbId **********");
        return userService.updateWbId(id, wbId);
    }

    @ApiOperation(notes = "update user's Zhihu ID,return the number of affected rows", value = "update Zhihu ID", httpMethod = "POST")
    @RequestMapping(path = "/auth/updateZhId")
    public ResponseEntity<?> updateZh(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        String zhId = params.get("zhId");
        System.out.println("********** updateZhId **********");
        return userService.updateZhId(id, zhId);
    }
}
