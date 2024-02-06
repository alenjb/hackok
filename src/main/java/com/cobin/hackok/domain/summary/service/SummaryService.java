package com.cobin.hackok.domain.summary.service;

import com.cobin.hackok.domain.global.config.IdConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public interface SummaryService {
    // 네이버 api 요약 기능
    public Map<String, Object> getSummary(com.cobin.hackok.domain.summary.dto.RequestBody requestBody);

    // open ai api 키워드 추츨 기능

}
