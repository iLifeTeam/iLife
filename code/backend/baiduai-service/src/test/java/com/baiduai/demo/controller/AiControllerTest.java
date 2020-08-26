package com.baiduai.demo.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class AiControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    String text = "[\" 你好\",\" 啊这\"]";

    @Test
    void analysis() throws Exception {
        MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.post("/baiduapi/analysis").
                        content(text).
                        contentType(MediaType.APPLICATION_JSON)
        ).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print()).
                andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void analysiswithkey() throws Exception {
        MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.post("/baiduapi/analysiswithkey").
                        param("ak","M7DUCv3BnfumFjfAmv15o0iV").
                        param("sk","HRcQdh4Xh0UZA6sULDZo8uYkG2jd7czY").
                        content(text).
                        contentType(MediaType.APPLICATION_JSON)
        ).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print()).
                andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void analysis1() throws Exception {
        MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.post("/baiduapi/analysis1").
                        param("weibo","峡谷之渊遥控车俱乐部").
                        contentType(MediaType.APPLICATION_JSON)
        ).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print()).
                andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}