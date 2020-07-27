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
        System.out.println(bilicrawller.loginconfirm("3c0e5ba333176d73d33ffe13e9889916"));
    }
    @Test
    void getHistory() throws IOException {
        System.out.println(bilicrawller.gethistory("7c38d094%2C1611034097%2Cbddbf*71"));
    }
    @Test
    void updataHistory() throws IOException {
        bilicrawller.updatehistory("7c38d094%2C1611034097%2Cbddbf*71");
    }
    @Test
    void getuser() throws IOException {
        System.out.println(bilicrawller.getuserinform("7c38d094%2C1611034097%2Cbddbf*71"));
    }
}