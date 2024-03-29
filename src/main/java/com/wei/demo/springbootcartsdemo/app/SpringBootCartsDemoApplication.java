package com.wei.demo.springbootcartsdemo.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.wei.demo.springbootcartsdemo")
@EntityScan(basePackages = "com.wei.demo.springbootcartsdemo.entity")
@EnableJpaRepositories("com.wei.demo.springbootcartsdemo.repository")
@EnableTransactionManagement
@EnableAsync
public class SpringBootCartsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCartsDemoApplication.class, args);
        log.info("成功啟動");
    }

}
