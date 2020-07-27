package com.ilife.jingdongservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
//@CrossOrigin(allowCredentials = "true", value = "http://49.234.125.131")
public class JingdongServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JingdongServiceApplication.class, args);
    }
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }
}
