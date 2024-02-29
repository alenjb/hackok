package com.cobin.hackok.domain.member.service;

import com.cobin.hackok.domain.member.dto.Member;

import java.util.Optional;

public interface MemberService {
    boolean signup(Member member);  // 회원가입
    Member login(Member member);    // 로그인
    Optional<Member> findPasswordByLoginIdAndName(String loginId, String name);   // 비밀번호 찾기

    Optional<Member> readMyInfo(String loginId); // 내정보 읽기(가져오기)
    
    Member changePassword(Member member);   // 비밀번호 변경
    Member changeMemberInfo(Member member);   // 회원정보 변경
}
