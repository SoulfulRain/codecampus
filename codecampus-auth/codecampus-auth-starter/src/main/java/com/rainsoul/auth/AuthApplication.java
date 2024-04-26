package com.rainsoul.auth;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 刷题微服务启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.rainsoul"})
@MapperScan(basePackages = {"com.rainsoul.**.mapper"})
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);

        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
