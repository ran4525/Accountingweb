package com.example.houduan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@MapperScan("com.example.houduan.mapper")
public class HouduanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouduanApplication.class, args);
    }

}
