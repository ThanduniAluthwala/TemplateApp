package com.qbitum.ifinitybanking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class IfinityBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(IfinityBankingApplication.class, args);
    }
}
