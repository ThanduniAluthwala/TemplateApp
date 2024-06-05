package com.qbitum.ifinitybanking.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /***
     * To be used for load balanced webClients with service discovery
     * @return
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    /***
     * To be used for webclient request urls with service IP and port
     * @return
     */
    @Bean
    public WebClient.Builder webClientBuilderDefault() {
        return WebClient.builder();
    }
}
