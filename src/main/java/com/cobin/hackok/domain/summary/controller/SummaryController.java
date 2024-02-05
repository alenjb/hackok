package com.cobin.hackok.domain.summary.controller;

import com.cobin.hackok.domain.global.config.IdConfig;
import com.cobin.hackok.domain.summary.dto.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @PostMapping("summarize")
    public void postRequest() {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";
        IdConfig idConfig = new IdConfig();

        WebClient webClient = WebClient.builder().baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", idConfig.getClientId())
                .defaultHeader("X-NCP-APIGW-API-KEY", idConfig.getClientSecret())
                .build();

        // 요청에 사용할 JSON 데이터를 생성
        RequestBody requestBody = new RequestBody();
        RequestBody.Document document = new RequestBody.Document();
        RequestBody.Option option = new RequestBody.Option();

        document.setTitle("하루 2000억' 판 커지는 간편송금 시장");
        document.setContent("간편송금 이용금액이 하루 평균 2000억원을 넘어섰다. 한국은행이 17일 발표한 '2019년 상반기중 전자지급서비스 이용 현황'에 따르면 올해 상반기 간편송금서비스 이용금액(일평균)은 지난해 하반기 대비 60.7% 증가한 2005억원으로 집계됐다. 같은 기간 이용건수(일평균)는 34.8% 늘어난 218만건이었다. 간편 송금 시장에는 선불전자지급서비스를 제공하는 전자금융업자와 금융기관 등이 참여하고 있다. 이용금액은 전자금융업자가 하루평균 1879억원, 금융기관이 126억원이었다. 한은은 카카오페이, 토스 등 간편송금 서비스를 제공하는 업체 간 경쟁이 심화되면서 이용규모가 크게 확대됐다고 분석했다. 국회 정무위원회 소속 바른미래당 유의동 의원에 따르면 카카오페이, 토스 등 선불전자지급서비스 제공업체는 지난해 마케팅 비용으로 1000억원 이상을 지출했다. 마케팅 비용 지출규모는 카카오페이가 491억원, 비바리퍼블리카(토스)가 134억원 등 순으로 많았다.");

        option.setLanguage("ko");
        option.setModel("news");
        option.setTone(2);
        option.setSummaryCount(3);

        requestBody.setDocument(document);
        requestBody.setOption(option);

        // POST 요청을 보내고 응답을 받아옴
        Map<String, Object> response = webClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        log.info(response.toString());
    }


    // 1. 텍스트 요약
    @GetMapping("text")
    public String summaryByText(){
        return "summary/summaryByText";
    }
    // 2. 이미지 요약
    // 3. 음성 요약
}
