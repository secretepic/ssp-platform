package com.boyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.boyu.mvc.mapper")
public class SspBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SspBeApplication.class, args);
    }
}
