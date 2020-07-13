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

@Api(tags = {"User Service Controller"}, description = "adsadas")
public class UserServiceController {


    @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info", httpMethod = "GET")
    @RequestMapping(path = "/auth/getById")
    public Users getUserById(@ApiParam(name = "userId", value = "The user ID of a iLife user") @RequestParam("userId") Long uid) {
        System.out.println("********** getUserById **********");
        return userService.findById(uid);
    }

    @ApiOperation(notes = "Get user info by Nickname", value = "get user info", httpMethod = "GET")
    @RequestMapping(path = "/auth/getByNickname")
    public Users getUserByNickname(@ApiParam(name = "nickname", value = "The user nickname of a iLife user") @RequestParam("nickname") String nickname) {
        System.out.println("********** getUserByNickname **********");
        return userService.findByNickname(nickname);
    }

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

    @ApiOperation(notes = "Save one iLife user by giving nickname,account.password and email", value = "delete one user", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "the nickname of the iLife user"),
            @ApiImplicitParam(name = "account", value = "the account of the iLife user"),
            @ApiImplicitParam(name = "password", value = "the password of the iLife user"),
            @ApiImplicitParam(name = "email", value = "the email of the iLife user")
    }
    )
    @RequestMapping(path = "/auth/logUp")
    public ResponseEntity<?> logUp(@ApiIgnore @RequestBody Map<String, String> params) {
        String nickname = params.get("nickname");
        String account = params.get("account");
        String password = params.get("password");
        String email = params.get("email");
        System.out.println("********** deleteById **********");
        return userService.save(nickname, account, password, email);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "wyyId", value = "the WangYiYun ID of the iLife user"),
            @ApiImplicitParam(name = "userId", value = "the user ID of the iLife user"),
    }
    )
    @ApiOperation(notes = "update user's WangYiYun ID", value = "update wyy ID", httpMethod = "POST")
    @RequestMapping(path = "/auth/updateWyyId")
    public ResponseEntity<?> updateWyy(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        Long wyyId = parseLong(params.get("wyyId"));
        System.out.println("********** updateWyyId **********");
        return userService.updateWyyId(id, wyyId);
    }

    @ApiOperation(notes = "update user's Weibo ID", value = "update Weibo ID", httpMethod = "POST")
    @RequestMapping(path = "/auth/updateWbId")
    public ResponseEntity<?> updateWb(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        Long wbId = parseLong(params.get("wbId"));
        System.out.println("********** updateWbId **********");
        return userService.updateWbId(id, wbId);
    }

    @ApiOperation(notes = "update user's Zhihu ID", value = "update Zhihu ID", httpMethod = "POST")
    @RequestMapping(path = "/auth/updateZhId")
    public ResponseEntity<?> updateZh(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        String zhId = params.get("zhId");
        System.out.println("********** updateZhId **********");
        return userService.updateZhId(id, zhId);
    }
}
