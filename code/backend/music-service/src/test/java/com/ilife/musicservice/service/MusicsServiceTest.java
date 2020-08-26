package com.ilife.musicservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MusicsServiceTest {
    @Autowired
    private MusicsService musicsService;

    @Test
    void getFavoriteSong() {
        System.out.println(musicsService.getFavoriteSong(562690552L));
    }
}