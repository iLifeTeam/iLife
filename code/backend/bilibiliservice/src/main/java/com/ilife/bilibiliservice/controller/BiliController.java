package com.ilife.bilibiliservice.controller;

import com.alibaba.fastjson.JSONArray;
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
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping("/bili/getloginurl")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONObject getloginurl() throws IOException {
        return JSONObject.parseObject(bilicrawller.getloginurl());
    }


    @PostMapping("/bili/loginconfirm")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String loginconfirmandgetSESSDATA(@RequestParam("oauthKey") String oauthKey) throws IOException {
        List<Cookie> cookies = bilicrawller.loginconfirm(oauthKey);
        if(cookies.size()<=1) return null;
        else return cookies.get(2).getValue();
    }


    @PostMapping("/bili/gethistory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Page<history> gethistory(@RequestParam("mid") Long mid, @RequestParam("page") Integer page, @RequestParam("size") Integer size)  {
        Pageable pageable = PageRequest.of(page, size);
        return historyService.findAllByMid(mid,pageable);
    }


    @GetMapping("/bili/userinform")
    @PreAuthorize("hasRole('ROLE_USER')")
    public biliuser getbiliuserinform(@RequestParam("SESSDATA") String SESSDATA) throws IOException {
        return bilicrawller.getuserinform(SESSDATA);
    }


    @GetMapping("/bili/updatehistory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String updatehistory(@RequestParam("SESSDATA") String SESSDATA) throws IOException {
        return bilicrawller.updatehistory(SESSDATA);
    }



    @GetMapping("/bili/getFavortag")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<String> getFavortag(@RequestParam("mid") Long mid)  {
        return historyService.getFavoriteTag(mid);
    }

    @GetMapping("/bili/getPopVideo")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONObject getPopVideo(@RequestParam("tag") String tag) throws IOException {
        int tid = historyService.getFavoriteTagid(tag);
        return bilicrawller.getPopVideo(tid);
    }

    @GetMapping("/bili/getFavorUp")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONArray getFavorUp(@RequestParam("mid") Long mid) throws IOException {
        List<Long> listofup = historyService.getFavoriteUp(mid);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < listofup.size();++i){
            JSONObject jsonObject = bilicrawller.getAuther(listofup.get(i));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @GetMapping("/bili/getUp")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONObject getUp(@RequestParam("mid") Long mid) throws IOException {
        return bilicrawller.getAuther(mid);
    }
    @GetMapping("/bili/getUpVideo")
    @PreAuthorize("hasRole('ROLE_USER')")
    public JSONObject getUpVideo(@RequestParam("mid") Long id) throws IOException {
        return bilicrawller.getAutherVideo(id);
    }

}
