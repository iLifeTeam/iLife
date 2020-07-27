package com.baiduai.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiduai.demo.api.baiduapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AiController {
    @Autowired
    private baiduapi baiduapi;


    @CrossOrigin
    @PostMapping("/baiduapi/analysis")
    public JSONObject analysis(@RequestBody List<String> weibo) throws IOException {
        int total = weibo.size();
        int positive= 0;
        int negative = 0;
        int neutral = 0;
        String token = baiduapi.getAuth();
        for (int i = 0; i < weibo.size();++i){
            int tmp = baiduapi.analysis(token,weibo.get(i));
            if(tmp == 0){negative++;}
            else if(tmp==1){neutral++;}
            else {positive++;}

        }
        String result = "{\"total\":"+String.valueOf(total)+"," +
                "\"positive\":" +String.valueOf(positive)+"," +
                "\"negative\":"+String.valueOf(negative)+"," +
                "\"neutral\":"+String.valueOf(neutral)+"}";
        JSONObject jsonObject =JSONObject.parseObject(result);
        return jsonObject;
    }

    @CrossOrigin
    @PostMapping("/baiduapi/analysiswithkey")
    public JSONObject analysiswithkey(@RequestBody List<String> weibo, @RequestParam String ak,@RequestParam String sk) throws IOException {
        int total = weibo.size();
        int positive= 0;
        int negative = 0;
        int neutral = 0;
        for (int i = 0; i < weibo.size();++i){
            int tmp = baiduapi.analysis(baiduapi.getAuth(ak,sk),weibo.get(i));
            switch (tmp){
                case 0: positive++;break;
                case 1: neutral++;break;
                default: positive++;break;
            }
        }
        String result = "{\"total\":"+String.valueOf(total)+"," +
                "\"positive\":" +String.valueOf(positive)+"," +
                "\"negative\":"+String.valueOf(negative)+"," +
                "\"neutral\":"+String.valueOf(neutral)+"}";
        JSONObject jsonObject =JSONObject.parseObject(result);
        return jsonObject;
    }

    @CrossOrigin
    @PostMapping("/baiduapi/analysis1")
    public int analysis1(@RequestParam String weibo) throws IOException {

        return baiduapi.analysis(baiduapi.getAuth(),weibo);
    }
}
