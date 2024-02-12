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
     * 키워드 추출 기능
     * 1. chat-gpt를 우선 사용
     * 2. chat-gpt 오류 시 matgim API 사용
     */
    // 키워드를 추출하는 메서드
    Map<String, Object> getKeywords(String content) throws JsonProcessingException;
    // 키워드를 매핑하는 메서드
    public Map<String, Object> mapKeywords(List<String> keywords);


}
