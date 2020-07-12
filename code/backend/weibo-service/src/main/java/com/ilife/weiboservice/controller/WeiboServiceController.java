package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class WeiboServiceController {

    @Autowired
    private WeiboService weiboService;

    @ApiOperation(notes = "Get all Weibos from database of one user specified by userID", value = "get user info",httpMethod = "GET")
    @RequestMapping(path="/weibo/getWeibo")
    public List<Weibo> getWeibo(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Integer uid){
        System.out.println("*****getWeibo*****");
        return weiboService.findAllByUid(uid);
    }
    @ApiOperation(notes = "Crawl Weibos of one user specified by userID,should be called when user ask to update the databse", value = "crawl Weibo ",httpMethod = "GET")
    @RequestMapping(path="/weibo/crawlWeibo")
    public void crawlWeibo(@ApiParam(name = "userId", value = "The user ID of a WeiBo user,should be a Long Integer") @RequestParam("userId") Long uid){
        System.out.println("*****crawlWeibo*****");
        weiboService.crawlWeibo(uid);
    }
}
