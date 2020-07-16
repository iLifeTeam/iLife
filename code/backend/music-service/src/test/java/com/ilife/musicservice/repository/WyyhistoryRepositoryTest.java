package com.ilife.musicservice.repository;

import com.ilife.musicservice.dao.WyyhistoryDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WyyhistoryRepositoryTest {
    @Autowired
    private WyyhistoryDao wyyhistoryDao;
    @Test
    void findbyidsortedbypagetest(){
        Pageable pageable = PageRequest.of(5,10);
        System.out.println(wyyhistoryDao.findAllbyid((long)417778610, pageable));
    }
}