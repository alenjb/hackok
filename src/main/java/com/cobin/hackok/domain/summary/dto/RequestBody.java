package com.cobin.hackok.domain.summary.dto;

import lombok.Data;

@Data
public class RequestBody {
    private Document document;
    private Option option;

    @Data
    public static class Document {
        private String title;
        private String content;

    }

    @Data
    public static class Option {
        private String language;
        private String model;
        private int tone;
        private int summaryCount;

    }

}