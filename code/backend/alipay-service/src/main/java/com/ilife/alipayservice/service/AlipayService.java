package com.ilife.alipayservice.service;

import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface AlipayService {

    List<Bill> getUserBillsBetween(Integer uid, Timestamp lower, Timestamp upper);
    List<Bill> getUserAllBills(Integer uid);
    User getUserWithPhone(String phone);
    Integer updateUserBills(Integer uid);
}
