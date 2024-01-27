package com.cobin.hackok.domain.summary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SummaryController {
    public String home(){
        return "index";
    }
}
