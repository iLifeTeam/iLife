package com.ilife.douban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class DoubanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoubanApplication.class, args);
    }

}
