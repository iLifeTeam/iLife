package com.ilife.musicservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WyyhistoryServiceTest {

    @Autowired
    private WyyhistoryService wyyhistoryService;

    @Test
    void findAllbyid() {
        assertEquals(100,wyyhistoryService.findAllbyid((long) 417778610).size());
    }

    @Test
    void testFindAllbyid() {
        Pageable pageable = PageRequest.of(5, 10);
        assertEquals(10,wyyhistoryService.findAllbyid((long) 417778610, pageable).getNumberOfElements());
        System.out.println(wyyhistoryService.findAllbyid((long) 417778610));
    }

    @Test
    void deletebyid() {

    }
}