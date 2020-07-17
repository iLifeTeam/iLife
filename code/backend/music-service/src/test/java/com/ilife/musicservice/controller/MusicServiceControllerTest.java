package com.ilife.musicservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicServiceControllerTest {

    @Autowired
    private MusicServiceController musicServiceController;
    private MockMvc mockMvc;
    @Test
    void gethistorybypage() {
//        String result = mockMvc.perform(post("/")
//                .param("a", "10")
//                .param("b", "11")
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
    }
}