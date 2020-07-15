package com.ilife.alipayservice.service;

import com.alibaba.fastjson.JSON;
import com.ilife.alipayservice.entity.Bill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlipayServiceTest {

    @Autowired
    AlipayService alipayService;

    @Test
    void contextLoads() {
    }
    @Test
    void getUserBillsBetween() {
    }

    @Test
    void loadBillsFromCSVFile() {
        Integer bills = alipayService.updateUserBills(1);
//        Assertions.assertEquals(bills,22);
    }
}