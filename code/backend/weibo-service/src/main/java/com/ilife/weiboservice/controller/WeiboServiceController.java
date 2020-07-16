package com.ilife.weiboservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WeiboServiceController {

//    @Autowired
//    DiscoveryClient discoveryClient;

    @RequestMapping(path="/weibo/dc")
    public String dc(){
//        String services = "Services: " + discoveryClient.getServices();
//        System.out.println(services);
//        return services;
        return  null;
    }
}
