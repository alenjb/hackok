package com.cobin.hackok.domain.summary.service;

import com.cobin.hackok.domain.global.config.IdConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class SummaryServiceImpl implements SummaryService{
    // 네이버 api 요약 기능
    public Map<String, Object> getSummary(com.cobin.hackok.domain.summary.dto.RequestBody requestBody) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";
        IdConfig idConfig = new IdConfig();

        WebClient webClient = WebClient.builder().baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", idConfig.getClientId())
                .defaultHeader("X-NCP-APIGW-API-KEY", idConfig.getClientSecret())
                .build();

        // POST 요청을 보내고 응답을 받아옴
        Map<String, Object> response = webClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        return response;
    }
}
