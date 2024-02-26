package com.cobin.hackok.domain.summary.service;

import com.cobin.hackok.domain.summary.dto.Summary;
import com.cobin.hackok.domain.summary.repository.SummaryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService{

    private final ChatgptService chatgptService;
    private final ApiProperties properties;
    private final SummaryRepository summaryRepository;
    final String requestMessageForOpenAI = "what is 5 keywords form below? please format the answer as a JSON object with the keys" +
            "(\"keyword1\",\"keyword2\",\"keyword3\",\"keyword4\",\"keyword5\")\n";

    public SummaryServiceImpl(ChatgptService chatgptService, ApiProperties properties, SummaryRepository summaryRepository) {
        this.chatgptService = chatgptService;
        this.properties = properties;
        this.summaryRepository = summaryRepository;
    }

    /** 요약 관련 기능 **/

    // 1. 네이버 api를 사용해 요약하는 메서드
    public Map<String, Object> getSummary(com.cobin.hackok.domain.summary.dto.RequestBody requestBody) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

        WebClient webClient = WebClient.builder().baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", properties.getClientId())
                .defaultHeader("X-NCP-APIGW-API-KEY", properties.getClientSecret())
                .build();

        // POST 요청을 보내고 응답을 받아옴
        return webClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    /** 키워드 추출 기능 **/

    // 1. 키워드를 가져오는 메서드(1. openAI로 추출 2. matgim API로 추출)
    @Override
    public Map<String, Object> getKeywords(String content) throws JsonProcessingException {
        try {
            // 1. openAI의 키워드 추출을 시도해서 성공하면 리턴
            return getKeywordsFromOpenAI(content);
        } catch (Exception e) {
            log.info("openAI 추출 실패 후 matgim API를 사용");
            // 2. openAI의 키워드 추출이 실패하면 matgim API를 사용해 키워드 추출 후 리턴
            Map<String, Object> response = getKeywordsFromMatgimAPI(content);

            // 응답을 매핑
            if (response != null && response.containsKey("result")) {
                List<String> keywordMap = getTop5Keywords(response);
                assert keywordMap != null;
                return mapKeywords(keywordMap);
            } else {
                System.out.println("Invalid response format or no result found.");
                return null;
            }
        }
    }

    // 2. open AI를 이용하여 키워드 추출
    public Map<String, Object> getKeywordsFromOpenAI(String content) throws Exception{
         // 키워드를 뽑는 멘트
         content = requestMessageForOpenAI + content;
         Map<String, Object> resultMap = convertJsonToMap(chatgptService.sendMessage(content));
         // 맵에서 값들만 빼서 리스트로 만들기
         List<String> keywords = new ArrayList<>();
         for (Object value : resultMap.values()) {
             keywords.add(removeQuotes(value.toString()));
         }
         return mapKeywords(keywords);
    }

    // 3. matgim API를 이용하여 키워드 추출
    private Map<String, Object> getKeywordsFromMatgimAPI(String content) {
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
        return response;
    }



    /** 키워드 처리 기능 **/

    // 1. 주어진 JSON 형식의 문자열을 Map으로 변환하는 메서드
    public Map<String, Object> convertJsonToMap(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString);
            return convertJsonNodeToMap(rootNode);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // 2. JSON 노드를 재귀적으로 탐색하여 Map으로 변환하는 메서드
    private Map<String, Object> convertJsonNodeToMap(JsonNode jsonNode) {
        Map<String, Object> resultMap = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            if (field.getValue().isObject()) {
                resultMap.put(field.getKey(), convertJsonNodeToMap(field.getValue()));
            } else {
                resultMap.put(field.getKey(), field.getValue().asText());
            }
        }
        return resultMap;
    }

    // 3. 상위 5개의 키워드를 가져오는 메서드(matgim API 사용 시에만 활용)
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

    // 4. 따옴표 제거
    private String removeQuotes(String keyword) {
        return keyword.replaceAll("^\"|\"$", "");
    }

    // 5. 키워드들을 map을 매핑하는 메서드
    @Override
    public Map<String, Object> mapKeywords(List<String> keywords) {
        HashMap<String, Object> keywordMap = new HashMap<>();
        for(String k:keywords){
        keywordMap.put("keyword1", removeQuotes(keywords.get(0)));
        keywordMap.put("keyword2", removeQuotes(keywords.get(1)));
        keywordMap.put("keyword3", removeQuotes(keywords.get(2)));
        keywordMap.put("keyword4", removeQuotes(keywords.get(3)));
        keywordMap.put("keyword5", removeQuotes(keywords.get(4)));
        }
        return keywordMap;
    }

    /**
     * 핵콕을 저장하는 기능
     **/
    @Override
    public Summary saveHackok(Summary summary) {
        return summaryRepository.save(summary);
    }

    /**
     * 핵콕을 리스트로 조회하는 기능
     */

    @Override
    public List<Summary> getHackoksByLoginId(String loginId) {
        return summaryRepository.findSummariesByUserIdOrderByIdDesc(loginId);
    }

    /**
     * 핵콕을 1개씩 조회하는 기능
     */
    @Override
    public Summary getHackokByLoginId(String loginId) {
        return summaryRepository.findSummaryByUserId(loginId);
    }
}
