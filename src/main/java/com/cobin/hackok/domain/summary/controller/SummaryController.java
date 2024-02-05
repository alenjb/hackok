package com.cobin.hackok.domain.summary.controller;

import com.cobin.hackok.domain.global.config.IdConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class SummaryController {
    public String home(HttpServletRequest request){
        if(request.getSession(false) == null) return "/login";
        return "index";
    }
    // 0. API를 통한 요약 요청
    @PostMapping(value = "/summarize/ajax")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> summarizeAjax(@RequestBody com.cobin.hackok.domain.summary.dto.RequestBody requestBody) {
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

        return ResponseEntity.ok(response);
    }


    // 1. 텍스트 요약
    @GetMapping("text")
    public String summaryByText(){
        return "summary/summaryByText";
    }
    // 2. 이미지 요약
    // 3. 음성 요약
}
