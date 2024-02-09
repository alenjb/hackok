package com.cobin.hackok.domain.summary.service;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
@Getter
public class ApiProperties {

    private String clientId;
    private String clientSecret;
}
