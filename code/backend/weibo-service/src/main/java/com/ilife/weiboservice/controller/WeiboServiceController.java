package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
public class WeiboServiceController {

    @Autowired
    private WeiboService weiboService;

    @ApiOperation(notes = "Get all Weibos from database of one user specified by userID", value = "get one user's Weibos", httpMethod = "GET")
    @RequestMapping(path = "/weibo/getWeibos")
    public List<Weibo> getWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Integer uid) {
        System.out.println("********** getWeibos **********");
        return weiboService.findAllByUid(uid);
    }

    @ApiOperation(notes = "Get One Weibo from database specified by Weibo ID", value = "get one Weibo", httpMethod = "GET")
    @RequestMapping(path = "/weibo/getWeibo")
    public Weibo getWeibo(@ApiParam(name = "Id", value = "The ID of a WeiBo,should be a String") @RequestParam("Id") String id) {
        System.out.println("********** getWeibo **********");
        return weiboService.findById(id);
    }

    @ApiOperation(notes = "Crawl Weibos of one user specified by userID,should be called when user ask to update the databse", value = "crawl Weibo ", httpMethod = "GET")
    @RequestMapping(path = "/weibo/crawlWeibo")
    public void crawlWeibo(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid) {
        System.out.println("********** crawlWeibo **********");
        weiboService.crawlWeibo(uid);
    }

    @ApiOperation(notes = "Delete all Weibos from database of one user specified by userID,success if the response.status = 200 ", value = "delete one user's Weibos", httpMethod = "GET")
    @RequestMapping(path = "/weibo/deleteWeibos")
    public ResponseEntity<?> deleteWeibos(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Integer uid) {
        System.out.println("********** deleteWeibos **********");
        return weiboService.deleteByUid(uid);
    }

    @ApiOperation(notes = "Delete one Weibos from database specified by Weibo ID,success if the response.status = 200 ", value = "delete one Weibo", httpMethod = "GET")
    @RequestMapping(path = "/weibo/deleteWeibo")
    public ResponseEntity<?> deleteWeibo(@ApiParam(name = "Id", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("Id") Integer id) {
        System.out.println("********** deleteWeibos **********");
        return weiboService.deleteById(id);
    }


}
