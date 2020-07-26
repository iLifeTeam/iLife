package com.ilife.bilibiliservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.ilife.bilibiliservice.crawller.bilicrawller;
import com.ilife.bilibiliservice.entity.biliuser;
import com.ilife.bilibiliservice.entity.history;
import com.ilife.bilibiliservice.service.HistoryService;
import io.swagger.annotations.Api;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Api
public class BiliController {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private bilicrawller bilicrawller;

    @CrossOrigin
    @GetMapping("/bili/getloginurl")
    public JSONObject getloginurl() {
        return JSONObject.parseObject(bilicrawller.getloginurl());
    }

    @CrossOrigin
    @PostMapping("/bili/loginconfirm")
    public String loginconfirmandgetSESSDATA(@RequestParam("oauthKey") String oauthKey) throws IOException {
        System.out.println(oauthKey);
        List<Cookie> cookies = bilicrawller.loginconfirm(oauthKey);
        System.out.println(cookies);
        if(cookies.size()<=1) return null;
        else return cookies.get(2).getValue();
    }

    @CrossOrigin
    @PostMapping("/bili/gethistory")
    public Page<history> gethistory(@RequestParam("mid") Long mid, @RequestParam("page") Integer page, @RequestParam("size") Integer size)  {
        Pageable pageable = PageRequest.of(page, size);
        return historyService.findAllByMid(mid,pageable);
    }

    @CrossOrigin
    @GetMapping("/bili/userinform")
    public biliuser getbiliuserinform(@RequestParam("SESSDATA") String SESSDATA) throws IOException {
        return bilicrawller.getuserinform(SESSDATA);
    }

    @CrossOrigin
    @GetMapping("/bili/updatehistory")
    public void updatehistory(@RequestParam("SESSDATA") String SESSDATA) throws IOException {
        bilicrawller.updatehistory(SESSDATA);
    }
}
