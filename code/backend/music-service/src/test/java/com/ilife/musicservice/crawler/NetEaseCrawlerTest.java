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
        assertEquals(562690552, netEaseCrawler.getuid("18804940083", "liyifeng0504521."));
    }
    @Test
    void test1() {
        netEaseCrawler.crawlbyid(netEaseCrawler.getuid("18804940083","liyifeng0504521."));
    }

    @Test
    void test3() {
        long id =3220012996L;
        netEaseCrawler.crawlbyid(417778610L);
    }
    @Test
    void test4(){
        System.out.println(netEaseCrawler.getimage(88926L));
        System.out.println(netEaseCrawler.getsimiSongs(88926L));
    }
}