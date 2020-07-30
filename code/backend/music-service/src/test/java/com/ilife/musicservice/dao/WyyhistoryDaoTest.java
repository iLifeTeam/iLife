package com.ilife.musicservice.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WyyhistoryDaoTest {
    @Autowired
    private WyyhistoryDao wyyhistoryDao;

    @Autowired
    private SingDao singDao;
    @Autowired
    private MusicsDao musicsDao;

    @Test
    void findAllbyid() {
        System.out.println(wyyhistoryDao.findAllbyid((long) 417778610).size());
    }

    @Test
    void testFindAllbyid() {
        Pageable pageable = PageRequest.of(5, 10);
        System.out.println(wyyhistoryDao.findAllbyid((long) 417778610, pageable));
    }

    @Test
    void addsongandsing() {
        musicsDao.addmusic((long) 1, "我好想你");
        singDao.addsing((long) 1, (long) 1, "吴青峰");
    }

    @Test
    void addsongandhistory() {
        wyyhistoryDao.addhistory((long) 1, (long) 1, 1, 1);
        System.out.println(wyyhistoryDao.deletebywyyid((long) 1));
    }
}