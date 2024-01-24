package com.cobin.hackok.domain.member.service;

import com.cobin.hackok.domain.member.dto.Member;

public interface MemberService {
    boolean signup(Member member);  // 회원가입
    Member login(Member member);    // 로그인

}
