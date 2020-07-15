package com.ilife.alipayservice.service;

import com.alibaba.fastjson.JSON;
import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.service.serviceimpl.AlipayServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
        AlipayServiceImpl.CSV_FILE_PATH = "src/test/java/com/ilife/alipayservice/service/test_账户明细_1.csv";
        Integer bills = alipayService.updateUserBills(1);
//        Assertions.assertEquals(bills,22);
    }
}