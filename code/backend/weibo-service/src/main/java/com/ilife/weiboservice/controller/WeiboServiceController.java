package com.ilife.weiboservice.controller;

import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;


@CrossOrigin(origins = "*")
@RestController
public class WeiboServiceController {

//    @Autowired
//    DiscoveryClient discoveryClient;

    @Autowired
    private WeiboService weiboService;
    @RequestMapping(path="/weibo/dc")
    public String dc(){
//        String services = "Services: " + discoveryClient.getServices();
//        System.out.println(services);
//        return services;
        return  null;
    }
    @RequestMapping(path="/weibo/getWeibo")
    public List<Weibo> getWeibo(@RequestParam("userId") Integer uid){
        System.out.println("*****getWeibo*****");
        return weiboService.findAllByUid(uid);
    }
    @RequestMapping(path="/weibo/crawlWeibo")
    public void crawlWeibo(@RequestParam("userId") Integer uid){
        System.out.println("*****getWeibo*****");
        weiboService.crawlWeibo(uid);
    }
}
