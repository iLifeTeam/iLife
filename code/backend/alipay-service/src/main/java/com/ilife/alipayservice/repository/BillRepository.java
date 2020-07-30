package com.ilife.alipayservice.repository;

import com.ilife.alipayservice.entity.Bill;
import com.ilife.alipayservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface BillRepository extends CrudRepository<Bill, String> {

    List<Bill> findByUidAndTimeBetween(Integer uid, Timestamp lower, Timestamp upper);
    List<Bill> findByUid(Integer uid);
}
