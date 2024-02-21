package com.cobin.hackok.domain.member.controller;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
public class MemberLoginController {

    private final MemberService service;

    @Autowired
    public MemberLoginController(MemberService service) {
        this.service = service;
    }
    //1. 로그인

    // 1-1. 로그인 폼
    @GetMapping("/login")
    public String loginForm(){
        return "login/loginForm";
    }

    //1-2. 로그인 실행
    @PostMapping("/login")
    public String doLogin(@ModelAttribute Member member, HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL){
        Member loginResult = service.login(member);
        if (loginResult == null){ // 로그인에 실패한 경우
           return "login/loginForm";
        }
        // 로그인이 성공한 경우
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginResult);
        log.info("로그인 성공");

        return "redirect:" + redirectURL;
    }

    //2. 회원가입

    // 2-1. 회원가입 폼
    @GetMapping("/signup")
    public String signupForm(){
        return "login/signupForm";
    }

    //2-2. 회원가입 실행
    @PostMapping("/signup")
    public String doSignup(Member member){
        boolean doSignup = service.signup(member);
        if(doSignup) log.info("회원가입 완료");

        return "redirect:login";
    }


    //3. 비밀번호 찾기

    //3-1. 비밀번호 찾기 폼
    @GetMapping("/forgotpassword")
    public String forgotPasswordForm(){
        return "login/forgotPasswordForm";
    }

    //3-2. 비밀번호 찾기 실행
    @PostMapping("/forgotpassword")
    public String findPassword(@RequestParam(name = "loginId") String loginId, @RequestParam(name = "name") String name, Model model){
        Optional<Member> findMember = service.findPasswordByLoginIdAndName(loginId, name);
        if(findMember.isPresent()) model.addAttribute("password", findMember.get().getPassword());
        return "login/noticePasswordForm";
    }


    //4. 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();  // 세션 무효화
        return "redirect:/login";
    }


}
