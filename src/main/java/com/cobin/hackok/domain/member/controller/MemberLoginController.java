package com.cobin.hackok.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class MemberLoginController {
    //1. 로그인
    @GetMapping
    public String Login(){
        return "login/loginForm";
    }
    //2. 회원가입
    //3. 아이디 찾기
    //4. 비밀번호 찾기
    //5. 회원 탈퇴

}
