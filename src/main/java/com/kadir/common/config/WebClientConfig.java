package com.kadir.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + "YOUR_OPENAI_API_KEY")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
