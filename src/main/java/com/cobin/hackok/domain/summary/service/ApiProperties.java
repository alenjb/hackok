package com.cobin.hackok.domain.summary.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "naver")
@Getter @Setter
public class ApiProperties {

    private String clientId;
    private String clientSecret;
}
