package com.github.desprd.repositoryfinder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(GitHubClient.class)
class HttpClientConfig{
    @Bean
    RestClientCustomizer restClientCustomizer(@Value("${github.api.url}") String baseUrl) {
        return restClientBuilder -> restClientBuilder.baseUrl(baseUrl);
    }
}