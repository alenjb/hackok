package com.cobin.hackok.domain.member.service;

import com.cobin.hackok.domain.member.dto.Member;

import java.util.Optional;

public interface MemberService {
    boolean signup(Member member);  // 회원가입
    Member login(Member member);    // 로그인

    Optional<Member> findPasswordByLoginId(String loginId);   // 비밀번호 찾기

}
