package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.Statistics;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@Api(value = "/pet")
public class WeiboServiceController {

    @Autowired
    private WeiboService weiboService;

    @ApiOperation(notes = "Get all Weibos from database of one user specified by userID", value = "get one user's Weibos", httpMethod = "GET")
    @GetMapping(path = "/weibo/getWeibos")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public List<Weibo> getWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid, HttpServletResponse response) {
        System.out.println("********** getWeibos **********");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return weiboService.findAllByUid(uid);
    }

    @ApiOperation(notes = "Get One Weibo from database specified by Weibo ID", value = "get one Weibo", httpMethod = "GET")
    @GetMapping(path = "/weibo/getWeibo")
  //  @PreAuthorize("hasRole('ROLE_USER')")
    public Weibo getWeibo(@ApiParam(name = "Id", value = "The ID of a WeiBo,should be a String") @RequestParam("Id") String id,HttpServletResponse response) {
        System.out.println("********** getWeibo **********");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return weiboService.findById(id);
    }

    @ApiResponses({
            @ApiResponse(code = 501, message = "userId not exists"),
    })
    @ApiOperation(notes = "Delete all Weibos from database of one user specified by userID,success if the response.status = 200 ", value = "delete one user's Weibos", httpMethod = "GET")
    @GetMapping(path = "/weibo/deleteWeibos")
  //  @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid,HttpServletResponse response){
        System.out.println("********** deleteWeibos **********");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return weiboService.deleteByUid(uid);
    }

    @ApiResponses({
            @ApiResponse(code = 501, message = "weiboId not exists"),
    })
    @ApiOperation(notes = "Delete one Weibos from database specified by Weibo ID,success if the response.status = 200 ", value = "delete one Weibo", httpMethod = "GET")
    @GetMapping(path = "/weibo/deleteWeibo")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteWeibo(@ApiParam(name = "Id", value = "The ID of a WeiBo,should be a String") @RequestParam("Id") String id,HttpServletResponse response) {
        System.out.println("********** deleteWeibos **********");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return weiboService.deleteById(id);
    }

    @ApiOperation(notes = "Get user's weibo statistics specified by userID", value = "get weibo statistics", httpMethod = "GET")
    @GetMapping(path = "/weibo/getStats")
//   @PreAuthorize("hasRole('ROLE_USER')")
    public Statistics getStats(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid,
                               @RequestParam("startTime") Date startTime,@RequestParam("endTime") Date endTime) {
        //TODO:解决时区问题
        System.out.println("********** getStats **********");
        return weiboService.getStats(uid,startTime,endTime);
    }


}
