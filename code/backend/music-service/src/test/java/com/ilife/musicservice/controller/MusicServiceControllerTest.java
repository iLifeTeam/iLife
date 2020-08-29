package com.ilife.musicservice.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class MusicServiceControllerTest {

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
    public void gethistory() throws Exception {
        MvcResult result = mockMvc.perform(post("/music/gethistorybypage")
                .param("ph", "18679480337")
                .param("pw", "Xiong0608")
                .param("size", "10")
                .param("page", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    @WithMockUser(roles="USER")
    public void updatehistory() throws Exception {
        MvcResult result = mockMvc.perform(post("/music/updatehistory")
                .param("ph", "18679480337")
                .param("pw", "Xiong0608")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }



    @Test

    @WithMockUser(roles="USER")
    public void updatehistorybyid() throws Exception {
        MvcResult result = mockMvc.perform(post("/music/updatehistorybyid")
                .param("id", "417778610")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser(roles="USER")
    public void gethistorybyid() throws Exception {
        MvcResult result = mockMvc.perform(post("/music/gethistorybyid")
                .param("id", "417778610")
                .param("size", "10")
                .param("page", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser(roles="USER")
    public void getFaovorSong() throws Exception {
        MvcResult result = mockMvc.perform(post("/music/getFavorSong")
                .param("ph", "18679480337")
                .param("pw", "Xiong0608")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(roles="USER")
    public void getFaovorSingers() throws Exception {
        MvcResult result = mockMvc.perform(post("/music/getFavorSingers")
                .param("ph", "18679480337")
                .param("pw", "Xiong0608")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}