package com.ilife.authservice.controller;


import com.ilife.authservice.entity.Users;
import com.ilife.authservice.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static java.lang.Long.parseLong;


@CrossOrigin(origins = "*")
@RestController
@Api(tags = {"User Service Controller"}, description = "Everything about auth & CRUD of iLife User")
public class UserServiceController {


    @Autowired
    private UserService userService;

    @ApiOperation(notes = "Get user info by userID", value = "get user info by id", httpMethod = "GET")
    @GetMapping(path = "/auth/getById")
    public Users getUserById(@ApiParam(name = "userId", value = "The user ID of a iLife user") @RequestParam("userId") Long uid) {
        System.out.println("********** getUserById **********");
        return userService.findById(uid);
    }

    @ApiOperation(notes = "Get user info by account", value = "get user info by account", httpMethod = "GET")
    @GetMapping(path = "/auth/getByAccount")
    public Users getUserByAccount(@ApiParam(name = "account", value = "The account number of a iLife user") @RequestParam("account") String account) {
        System.out.println("********** getUserByAccount **********");
        return userService.findByAccount(account);
    }

    @ApiOperation(notes = "Get user info by Nickname", value = "get user info by nickname", httpMethod = "GET")
    @GetMapping(path = "/auth/getByNickname")
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
    @PostMapping(path = "/auth/delById")
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
            @ApiImplicitParam(name = "email", value = "the email of the iLife user"),
            @ApiImplicitParam(name = "type", value = "the type of the iLife user")
    }
    )
    @PostMapping(path = "/auth/register")
    public ResponseEntity<?> register(@ApiIgnore @RequestBody Map<String, String> params) {
        String nickname = params.get("nickname");
        String account = params.get("account");
        String password = params.get("password");
        String email = params.get("email");
        String type = params.get("type");
        System.out.println("********** register **********");
        return userService.save(nickname, account, password, email,type);
    }


//    @ApiOperation(notes = "Auth one iLife user by giving account.password", value = "User log in", httpMethod = "POST")
//    @ApiResponses({
//            @ApiResponse(code = 501, message = "user not exists"),
//            @ApiResponse(code = 502, message = "account and password not match"),
//    })
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "account", value = "the account of the iLife user"),
//            @ApiImplicitParam(name = "password", value = "the password of the iLife user"),
//    }
//    )
//    @PostMapping(path = "/auth/auth")
//    public ResponseEntity<?> auth(@ApiIgnore @RequestBody Map<String, String> params) {
//        String account = params.get("account");
//        String password = params.get("password");
//        System.out.println("********** auth **********");
//        return userService.auth(account, password);
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "wyyId", value = "the WangYiYun ID of the iLife user"),
            @ApiImplicitParam(name = "userId", value = "the user ID of the iLife user"),
    }
    )
    @ApiOperation(notes = "update user's WangYiYun ID，return the number of affected rows", value = "update wyy ID", httpMethod = "POST")
    @PostMapping(path = "/auth/updateWyyId")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateWyy(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        Long wyyId = parseLong(params.get("wyyId"));
        System.out.println("********** updateWyyId **********");
        return userService.updateWyyId(id, wyyId);
    }

    @ApiOperation(notes = "update user's Weibo ID,return the number of affected rows", value = "update Weibo ID", httpMethod = "POST")
    @PostMapping(path = "/auth/updateWbId")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateWb(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        Long wbId = parseLong(params.get("wbId"));
        System.out.println("********** updateWbId **********");
        return userService.updateWbId(id, wbId);
    }

    @ApiOperation(notes = "update user's Zhihu ID,return the number of affected rows", value = "update Zhihu ID", httpMethod = "POST")
    @PostMapping(path = "/auth/updateZhId")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateZh(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        String zhId = params.get("zhId");
        System.out.println("********** updateZhId **********");
        return userService.updateZhId(id, zhId);
    }

    @ApiOperation(notes = "update user's Douban ID,return the number of affected rows", value = "update Douban ID", httpMethod = "POST")
    @PostMapping(path = "/auth/updateDbId")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateDb(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        String dbId = params.get("dbId");
        System.out.println("********** updateDbId **********");
        return userService.updateDbId(id, dbId);
    }

    @ApiOperation(notes = "update user's Bilibili ID,return the number of affected rows", value = "update Bilibili ID", httpMethod = "POST")
    @PostMapping(path = "/auth/updateBiliId")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateBili(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        String biliId = params.get("biliId");
        System.out.println("********** updateBiliId **********");
        return userService.updateBiliId(id, biliId);
    }

    @ApiOperation(notes = "update user's Taobao ID,return the number of affected rows", value = "update Taobao ID", httpMethod = "POST")
    @PostMapping(path = "/auth/updateTbId")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateTb(@ApiIgnore @RequestBody Map<String, String> params) {
        Long id = parseLong(params.get("userId"));
        String tbId = params.get("TbId");
        System.out.println("********** updateTaobaoId **********");
        return userService.updateTbId(id, tbId);
    }

    //when not login
    @ApiOperation(notes = "default login url for Spring Security", value = "update Weibo ID", httpMethod = "POST")
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.status(403).body("not login");
    }


}
