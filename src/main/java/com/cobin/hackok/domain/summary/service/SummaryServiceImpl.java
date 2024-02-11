package com.cobin.hackok.domain.summary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    /**
     * // 키워드 추출의 open AI version
     *
     * @Override public Map<String, Object> getKeywords(String content) {
     * // 키워드를 뽑는 멘트
     * content = requestMent + content;
     * <p>
     * // ChatGPT 에게 질문 후 매핑해서 리턴
     * return mapKeywords(chatgptService.sendMessage(content));
     * }
     */

    // 키워드 추출의 matgim.ai version
    @Override
    public Map<String, Object> getKeywords(String content) throws JsonProcessingException {
        String apiUrl = "https://api.matgim.ai/54edkvw2hn/api-keyword";

        WebClient webClient = WebClient.builder().baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-auth-token", properties.getMatigimApiKey())
                .build();

        // POST 요청을 보내고 응답을 받아옴
        Map<String, Object> response = webClient.post()
                .body(BodyInserters.fromValue(Map.of("document", content)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        // 응답을 매핑

        if (response != null && response.containsKey("result")) {
            List<String> keywordMap = getTop5Keywords(response);
            assert keywordMap != null;
            return mapKeywords(keywordMap);
        } else {
            System.out.println("Invalid response format or no result found.");
        }
        return null;
    }

    private static List<String> getTop5Keywords(Map<String, Object> response) {
        Map<String, Object> result = (Map<String, Object>) response.get("result");
        if (result.containsKey("keywords")) {
            List<List<Object>> keywords = (List<List<Object>>) result.get("keywords");
            // 출현 빈도를 기준으로 내림차순으로 정렬하기 위해 TreeMap을 사용
            TreeMap<String, Integer> keywordMap = new TreeMap<>();
            for (List<Object> keyword : keywords) {
                String word = (String) keyword.get(0);
                int frequency = (int) keyword.get(1);
                keywordMap.put(word, frequency);
            }

            // 출현 빈도가 높은 순서대로 상위 5개 키워드를 추출하여 리스트로 반환
            return keywordMap.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(5)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } else {
            System.out.println("No keywords found in the response.");
        }
        return null;
    }

    @Override
    public Map<String, Object> mapKeywords(List<String> keywords) {
        HashMap<String, Object> keywordMap = new HashMap<>();
        for(String k:keywords){
        keywordMap.put("keyword1", keywords.get(0));
        keywordMap.put("keyword2", keywords.get(1));
        keywordMap.put("keyword3", keywords.get(2));
        keywordMap.put("keyword4", keywords.get(3));
        keywordMap.put("keyword5", keywords.get(4));
        }
        return keywordMap;
    }
    /**
     *
    // 따옴표 제거
    private String removeQuotes(String keyword) {
        return keyword.replaceAll("^\"|\"$", "");
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
     */
}
