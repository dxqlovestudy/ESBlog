package com.xq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.xq.mapper")
//@MapperScan("com.xq")
@EnableScheduling
@EnableSwagger2
public class EasyBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyBlogApplication.class, args);
    }
}
