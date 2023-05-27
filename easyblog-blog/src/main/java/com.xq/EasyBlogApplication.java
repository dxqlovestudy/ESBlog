package com.xq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xq.mapper")
public class EasyBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyBlogApplication.class, args);
    }
}
