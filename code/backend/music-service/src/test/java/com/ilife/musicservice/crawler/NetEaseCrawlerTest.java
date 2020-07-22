package com.ilife.musicservice.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NetEaseCrawlerTest {
    @Autowired
    private NetEaseCrawler netEaseCrawler;

    @Test
    void test2() {
        assertEquals(417778610,netEaseCrawler.getuid("18679480337","Xiong0608"));
    }

    @Test
    void test3() {
       netEaseCrawler.crawlbyid(netEaseCrawler.getuid("18679480337","Xiong0608"));
    }
}