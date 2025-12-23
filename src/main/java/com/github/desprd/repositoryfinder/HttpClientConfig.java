package com.github.desprd.repositoryfinder;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(GitHubClient.class)
public class HttpClientConfig {

}