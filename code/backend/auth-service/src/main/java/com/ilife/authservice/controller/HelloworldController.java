package com.ilife.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class HelloworldController {
    @RestController
    public class HelloController {
        @GetMapping("/hello")
        public String hello() {
            return "hello";
        }
    }
}
