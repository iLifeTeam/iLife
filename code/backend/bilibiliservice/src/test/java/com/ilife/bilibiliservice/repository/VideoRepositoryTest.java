package com.ilife.bilibiliservice.repository;

import com.ilife.bilibiliservice.entity.biliuser;
import com.ilife.bilibiliservice.entity.video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoRepositoryTest {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private BiliuserRepostory biliuserRepostory;
    @Autowired
    private HistoryRepository historyRepository;
    @Test
    void test(){
        videoRepository.addvideo((long)1,"11","11",(long)1,"11","11");
    }
    @Test
    void find(){

        System.out.println(historyRepository.findAllByMid((long)480346309));


    }
    @Test
    void testadd(){
        biliuser biliuser = new biliuser();
        biliuser.setMid((long)1);
        biliuser.setUname("111");
        biliuserRepostory.save(biliuser);
    }
    @Test
    void testaddhistory(){
        historyRepository.addhistory((long)1,(long)1,"11",false);
        historyRepository.addhistory((long)1,(long)1,"11",false);
    }

    @Test
    void getUp(){
        System.out.println(historyRepository.getFavoriteUp((long)35159960));
    }

}