package com.cobin.hackok.domain.summary.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface SummaryService {
    /**
     *
     * 네이버 api 요약 기능
     */
    public Map<String, Object> getSummary(com.cobin.hackok.domain.summary.dto.RequestBody requestBody);


    /**
     * openAI api 기능
     */
    // chat gpt에게 요청
//    Map<String, Object> getKeywords(String content);
    //키워드들을 맵 형태로 변환
//    Map<String, Object> mapKeywords(String keywords);

    /**
     * matgim API 기능
     */
    Map<String, Object> getKeywords(String content) throws JsonProcessingException;
    public Map<String, Object> mapKeywords(List<String> keywords);


}
