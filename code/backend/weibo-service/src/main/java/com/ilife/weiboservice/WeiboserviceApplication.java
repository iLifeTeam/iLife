package com.ilife.weiboservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class WeiboserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeiboserviceApplication.class, args);
    }

}
