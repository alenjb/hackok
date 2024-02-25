package com.cobin.hackok.domain.summary.controller;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.summary.dto.Summary;
import com.cobin.hackok.domain.summary.service.SummaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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
    public String home(HttpServletRequest request, HttpSession session){
        if(request.getSession(false) == null) return "/login";
        return "index";
    }
    // 0. API를 통한 요약 요청
    @PostMapping(value = "/summarize/ajax")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> summarizeAjax(@RequestBody com.cobin.hackok.domain.summary.dto.RequestBody requestBody) throws JsonProcessingException {

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

    // 1-1. 텍스트 요약
    @GetMapping("text")
    public String summaryByText(HttpSession session, Model model){
        // 세션에서 userId를 가져와서 모델에 추가
        Member member = (Member) session.getAttribute("loginMember");
        ObjectId memberId = member.getId();
        String loginId = member.getLoginId();
        model.addAttribute("memberId", memberId);
        model.addAttribute("loginId", loginId);
        return "summary/summaryByText";
    }
    // 1-2. 이미지 요약
    // 1-3. 음성 요약

    // 2. 핵콕 저장
    @PostMapping("/save")
    public String saveHackok(@RequestParam("memberId") String memberId,
                             @RequestParam("loginId") String loginId,
                             @RequestParam("rawText") String rawText,
                             @RequestParam("title") String title,
                             @RequestParam("keywords") List<String> keywords,
                             @RequestParam("summaryText") String summaryText,
                             @RequestParam("date") String date,
                             RedirectAttributes redirectAttributes){
        Summary saveResult = service.saveHackok(new Summary(new ObjectId(), loginId, rawText, title, keywords, summaryText, date));

        if(saveResult == null){ // 핵콕 저장에 실패한 경우
            log.error("핵콕 저장 과정에 오류가 발생하였습니다.");
            redirectAttributes.addFlashAttribute("error", "핵콕 저장 과정에 오류가 발생하였습니다.");
            return "redirect:/summary/summaryByText";
        }
        return "/index";
    }

    // 3. 핵콕 리스트 조회(최근 기록)
    @GetMapping("/list")
    public String hackokList(HttpSession session, Model model){
        Member member = (Member) session.getAttribute("loginMember");
        ObjectId memberId = member.getId();
        String loginId = member.getLoginId();

        model.addAttribute("memberId", memberId);
        model.addAttribute("loginId", loginId);

        List<Summary> hackoksByLoginId = service.getHackoksByLoginId(loginId);
        model.addAttribute("hackoksList", hackoksByLoginId);

        return "summary/summaryList";
    }

    // 4. 핵콕 상세 조회(최근 기록)
    @GetMapping("/view/{userId}")
    public String hackokDetail(@PathVariable String userId, HttpSession session, Model model){
        Member member = (Member) session.getAttribute("loginMember");
        ObjectId memberId = member.getId();
        String loginId = member.getLoginId();

        Summary hackokByLoginId = service.getHackokByLoginId(loginId);
        if(hackokByLoginId.getUserId().equals(loginId)){    // 자신의 게시물이 맞는 경우
            model.addAttribute("hackok", hackokByLoginId);
            return "/summary/summaryDetailByText";
        }else {     // 자신의 게시물이 아닌 경우
            return "summary/summaryList";
        }
    }
}
