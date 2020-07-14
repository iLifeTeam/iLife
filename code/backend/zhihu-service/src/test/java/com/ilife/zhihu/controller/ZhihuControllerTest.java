package com.ilife.zhihu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
class ZhihuControllerTest {


    @Autowired
    ZhihuController zhihuController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    void saveImageString() {

    }

    @Test
    void loginIntoZhihu() {
    }

    @Test
    void updateUserActivities() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getActivity() {
    }

    @Test
    void getArticle() {
    }

    @Test
    void getQuestion() {
    }

    @Test
    void getAnswer() {
    }
}