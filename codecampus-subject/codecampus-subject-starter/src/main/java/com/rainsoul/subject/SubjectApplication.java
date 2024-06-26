package com.rainsoul.subject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 刷题微服务启动类
 *
 * @author RainSoul
 * @create 2024/4/21
 */
@SpringBootApplication
@ComponentScan("com.rainsoul")
@MapperScan("com.rainsoul.**.mapper")
public class SubjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class);
    }

}
