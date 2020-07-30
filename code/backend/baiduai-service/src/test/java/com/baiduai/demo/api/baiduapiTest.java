package com.baiduai.demo.api;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class baiduapiTest {
    @Autowired
    private baiduapi baiduapi;


    @Test
    void test() throws IOException {
        System.out.println(baiduapi.analysis(baiduapi.getAuth(),"门"));
    }

    @Test
    void testjson(){
        int total = 0;
        int positive= 0;
        int negative = 0;
        int neutral = 0;
        String result = "{\"total\":"+String.valueOf(total)+"," +
                "\"positive\":" +String.valueOf(positive)+"," +
                "\"negative\":"+String.valueOf(negative)+"," +
                "\"neutral\":"+String.valueOf(neutral)+"}";
        JSONObject jsonObject =JSONObject.parseObject(result);

        System.out.println(jsonObject.getInteger("total"));
    }
}