package com.ilife.bilibiliservice.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class BiliControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    @WithMockUser(roles="USER")
    void getloginurl() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/getloginurl")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void loginconfirmandgetSESSDATA() throws Exception {
        MvcResult result = mockMvc.perform(post("/bili/loginconfirm")
                .param("oauthKey", "4d7466abe7563caa216a4c84b6bfd1d1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void updatehistory() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/updatehistory")
                .param("SESSDATA", "7c38d094%2C1611034097%2Cbddbf*71")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void getuserinform() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/userinform")
                .param("SESSDATA", "7c38d094%2C1611034097%2Cbddbf*71")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void gethistory() throws Exception {
        MvcResult result = mockMvc.perform(post("/bili/gethistory")
                .param("mid", "480346309")
                .param("size", "10")
                .param("page", "3")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void getTag() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/getFavortag")
                .param("mid", "35159960")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void getUp() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/getFavorUp")
                .param("mid", "35159960")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void getPop() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/getPopVideo")
                .param("tag", "日常")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser(roles="USER")
    void getUpVideo() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/getUpVideo")
                .param("mid", "5970160")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    void getAnUp() throws Exception {
        MvcResult result = mockMvc.perform(get("/bili/getUp")
                .param("mid", "15875324")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}