package com.cobin.hackok.domain.member.controller;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
public class MemberLoginController {
    MemberService service;
    //1. 로그인

    // 1-1. 로그인 폼
    @GetMapping("/login")
    public String loginForm(){
        return "login/loginForm";
    }

    //1-2. 로그인 실행
    @PostMapping("/login")
    public String doLogin(Member member){
        service.login(member);
        return "index";
    }

    //2. 회원가입

    // 2-1. 회원가입 폼
    @GetMapping("/signup")
    public String signupForm(){
        return "login/signupForm";
    }

    //1-2. 회원가입 실행
    @PostMapping("/signup")
    public String doSignup(Member member){
        service.signup(member);
        return "redirec:login";
    }
    //3. 아이디 찾기
    //4. 비밀번호 찾기
    //5. 회원 탈퇴

}
