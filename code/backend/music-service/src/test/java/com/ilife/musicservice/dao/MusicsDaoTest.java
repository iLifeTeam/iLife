package com.ilife.musicservice.dao;

import com.ilife.musicservice.entity.musics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MusicsDaoTest {


    @Autowired
    private MusicsDao musicsDao;


    @Test
    void addmusic() {
    }

    @Test
    void findById() {
        musics Musics= musicsDao.findById(108390L);
        System.out.println(Musics);
    }

    @Test
    void getFavoriteSong() {
        System.out.println(musicsDao.getFavoriteSong(562690552L));

    }
}