package com.ilife.alipayservice.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.ilife.alipayservice.entity.Bill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


class CSVLoaderTest {

    private static CSVLoader loader;
    @BeforeAll
    static void init(){
        loader = new CSVLoader();
    }

    @Test
    void loadBillsFromCSVFile() {
        List<Bill> bills = loader.loadBillsFromCSVFile("src/test/java/com/ilife/alipayservice/service/test_账户明细_1.csv");
        System.out.println(bills.size());
        for (Bill bill: bills){
            System.out.println(JSON.toJSONString(bill));
        }
    }
}