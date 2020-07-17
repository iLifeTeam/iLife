package com.ilife.alipayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*") //TODO: remove later
public class AlipayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlipayServiceApplication.class, args);
    }

}
