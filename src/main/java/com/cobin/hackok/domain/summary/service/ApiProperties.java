package com.cobin.hackok.domain.summary.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
@Getter @Setter
public class ApiProperties {
    /**
     * 네이버 API 정보
     */
    private String clientId;
    private String clientSecret;
    /**
     * matgim API 정보
     */
    private String matigimApiKey;


}
