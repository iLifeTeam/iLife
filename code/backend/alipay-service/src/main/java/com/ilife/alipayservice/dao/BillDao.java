package com.ilife.alipayservice.dao;

import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface BillDao {

    List<Bill> findByUidAndTimeBetween(Integer uid, Timestamp lower, Timestamp upper);
    List<Bill> findByUid(Integer uid);
    Bill save(Bill bill);

}
