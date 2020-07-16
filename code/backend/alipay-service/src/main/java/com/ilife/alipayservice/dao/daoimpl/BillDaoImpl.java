package com.ilife.alipayservice.dao.daoimpl;

import com.ilife.alipayservice.dao.BillDao;
import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.entity.User;
import com.ilife.alipayservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;



@Repository
public class BillDaoImpl implements BillDao {
    @Autowired
    BillRepository billRepository;


    @Override
    public List<Bill> findByUidAndTimeBetween(Integer uid, Timestamp lower, Timestamp upper) {
        return billRepository.findByUidAndTimeBetween(uid, lower,upper);
    }

    @Override
    public List<Bill> findByUid(Integer uid) {
        return billRepository.findByUid(uid);
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }
}
