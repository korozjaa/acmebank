package com.onegini.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.onegini"})
@SpringBootApplication
public class AcmeBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcmeBankApplication.class, args);
    }
}
