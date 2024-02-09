package com.cobin.hackok.domain.summary.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService{

    private final ChatgptService chatgptService;
    private final ApiProperties properties;
    final String requestMent = "다음 나오는 내용의 핵심단어 5개를 뽑아서 콤마로 구분해줘.\n";

    public SummaryServiceImpl(ChatgptService chatgptService, ApiProperties properties) {
        this.chatgptService = chatgptService;
        this.properties = properties;
    }

    // 네이버 api 요약 기능
    public Map<String, Object> getSummary(com.cobin.hackok.domain.summary.dto.RequestBody requestBody) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

        WebClient webClient = WebClient.builder().baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", properties.getClientId())
                .defaultHeader("X-NCP-APIGW-API-KEY", properties.getClientSecret())
                .build();

        // POST 요청을 보내고 응답을 받아옴
        Map<String, Object> response = webClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        return response;
    }

    @Override
    public Map<String, Object> getKeywords(String content) {
        // 키워드를 뽑는 멘트
        content = requestMent + content;

        // ChatGPT 에게 질문 후 매핑해서 리턴
        return mapKeywords(chatgptService.sendMessage(content));
    }

    @Override
    public Map<String, Object> mapKeywords(String keywords) {
        log.info("키워드" + keywords);
        String[] splitKeywords = keywords.split(",");
        for(String k:splitKeywords){
            log.info("나눈키워드" + k);
        }
        HashMap<String, Object> keywordMap = new HashMap<>();
        keywordMap.put("keyword1", removeQuotes(splitKeywords[0]));
        keywordMap.put("keyword2", removeQuotes(splitKeywords[1]));
        keywordMap.put("keyword3", removeQuotes(splitKeywords[2]));
        keywordMap.put("keyword4", removeQuotes(splitKeywords[3]));
        keywordMap.put("keyword5", removeQuotes(splitKeywords[4]));
        return keywordMap;
    }

    // 따옴표 제거
    private String removeQuotes(String keyword) {
        return keyword.replaceAll("^\"|\"$", "");
    }
}
