package com.cobin.hackok.domain.summary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Summary {
    private Long id;                    // 고유 아이디
    @NotBlank
    private String userId;              // 사용자의 아이디
    @NotBlank
    private String rawText;             // 사용자가 입력한 요약 전 텍스트
    @NotBlank
    private String title;               // 제목
    @NotBlank
    private List<String> keywords;      // 핵심 키워드들
    @NotBlank
    private String summaryText;         // 요약된 텍스트
}
