package com.example.board.config;


import com.example.board.aop.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
