package com.ilife.bilibiliservice.crawller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class bilicrawllerTest {
    @Autowired
    private bilicrawller bilicrawller;

    @Test
    void getloginurl() {
        System.out.println(bilicrawller.getloginurl());
    }

    @Test
    void loginconfirm() throws IOException {
        System.out.println(bilicrawller.loginconfirm("ec473d74ed1a69facbadc05f3d504068"));
    }
    @Test
    void getHistory() throws IOException {
        System.out.println(bilicrawller.gethistory("7c38d094%2C1611034097%2Cbddbf*71"));
    }
    @Test
    void updataHistory() throws IOException {
        bilicrawller.updatehistory("73b7c81e%2C1613980001%2Cb5483*81");
    }
    @Test
    void getuser() throws IOException {
        System.out.println(bilicrawller.getuserinform("73b7c81e%2C1613980001%2Cb5483*81"));
    }

    @Test
    void getPopVideo() throws IOException {
        System.out.println(bilicrawller.getPopVideo(138));
    }
    @Test
    void getAuther() throws IOException {
        System.out.println(bilicrawller.getAuther(35159960L));
    }
    @Test
    void getAutherVideo() throws IOException {
        System.out.println(bilicrawller.getAutherVideo(5970160L));
    }
}