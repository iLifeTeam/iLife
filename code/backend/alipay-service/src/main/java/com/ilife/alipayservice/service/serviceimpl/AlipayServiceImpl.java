package com.ilife.alipayservice.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.ilife.alipayservice.dao.BillDao;
import com.ilife.alipayservice.dao.UserDao;
import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.entity.User;
import com.ilife.alipayservice.service.AlipayService;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.LineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AlipayServiceImpl implements AlipayService {


    @Autowired
    BillDao billDao;
    @Autowired
    UserDao userDao;

    CSVLoader csvLoader;

    public AlipayServiceImpl() {
        this.csvLoader = new CSVLoader();
    }


    @Override
    public List<Bill> getUserBillsBetween(Integer uid, Timestamp lower, Timestamp upper) {
        return billDao.findByUidAndTimeBetween(uid,lower,upper);
    }

    @Override
    public List<Bill> getUserAllBills(Integer uid) {
        return billDao.findByUid(uid);
    }

    @Override
    public User getUserWithPhone(String phone) {
        return userDao.findByPhone(phone);
    }


    private static final String TOTAL_SUFFIX = "_账务明细(汇总)_1.csv";
    private static final String DETAIL_SUFFIX = "_账务明细_1.csv";
    public static String CSV_FILE_PATH = "bills/" + "202006_2088512817100920/20885128171009200156_202006" + DETAIL_SUFFIX;
    @Override
    public Integer updateUserBills(Integer uid){
        List<Bill> bills = csvLoader.loadBillsFromCSVFile(CSV_FILE_PATH);

        bills.forEach(
                bill -> {
                    bill.setUid(uid);
                    System.out.println(JSON.toJSONString(bill));
                    Bill savedBill = billDao.save(bill);
                }
        );

        return bills.size();
    }

}
