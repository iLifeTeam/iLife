package com.ilife.bilibiliservice.service;

import com.ilife.bilibiliservice.dao.HistoryDao;
import com.ilife.bilibiliservice.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HistoryServiceTest {
    @Autowired
    private HistoryService historyService;


    @Test
    void findAllByMid() {
        System.out.println(historyService.findAllByMid((long)35159960));
    }

    @Test
    void getTag(){
        System.out.println(historyService.getFavoriteTag((long)35159960));
        System.out.println(historyService.getFavoriteTagid("搞笑"));
    }
}