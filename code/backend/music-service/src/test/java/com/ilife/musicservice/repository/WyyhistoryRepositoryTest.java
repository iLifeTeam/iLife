package com.ilife.musicservice.repository;

import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.entity.sing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WyyhistoryRepositoryTest {
    @Autowired
    private MusicsRepository musicsRepository;


    @Test
    void test(){

        sing sing = new sing();
        musics musics = new musics();
        musics.setMid((long) 5);
        musics.setMname("遥远的她");
        sing.setMid((long) 5);
        sing.setSid((long) 5);
        sing.setSname("张学友");
        musics.getSingers().add(sing);
        musicsRepository.save(musics);
    }

}