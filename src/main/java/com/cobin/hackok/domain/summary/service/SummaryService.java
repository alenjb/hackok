package com.cobin.hackok.domain.summary.service;

import com.cobin.hackok.domain.summary.dto.Summary;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * 핵콕에 저장 기능
     */
    public Summary saveHackok(Summary summary);

    /**
     * 핵콕을 리스트로 조회하는 기능
     */
    public List<Summary> getHackoksByLoginId(String loginId);

    /**
     * 핵콕을 1개씩 조회하는 기능
     */
    public Summary getHackokByLoginId(String loginId);

    /**
     * 핵콕 리스트 페이지 네이션 기능
     */
    public Page<Summary> getSummariesByUserId(Pageable pageable, String loginId);

}
