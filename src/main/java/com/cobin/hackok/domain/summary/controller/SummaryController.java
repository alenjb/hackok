package com.cobin.hackok.domain.summary.controller;

import com.cobin.hackok.domain.summary.service.SummaryService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class SummaryController {
    private final SummaryService service;
    private final ChatgptService chatgptService;

    @Autowired
    public SummaryController(SummaryService service, ChatgptService chatgptService) {
        this.service = service;
        this.chatgptService = chatgptService;
    }

    public String home(HttpServletRequest request){
        if(request.getSession(false) == null) return "/login";
        return "index";
    }
    // 0. API를 통한 요약 요청
    @PostMapping(value = "/summarize/ajax")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> summarizeAjax(@RequestBody com.cobin.hackok.domain.summary.dto.RequestBody requestBody) {
        // 네이버 API 사용 부분
        Map<String, Object> summary = service.getSummary(requestBody);
        // openAI API 사용 부분
        Map<String, Object> keywords = service.getKeywords(requestBody.getDocument().getContent());

        // 각 API에서 가져온 데이터를 병합
        Map<String, Object> response = new HashMap<>();
        response.putAll(summary);
        response.putAll(keywords);
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
