package com.cobin.hackok.domain.member.repository;

import com.cobin.hackok.domain.member.dto.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);    // 회원 저장
    Member update(Member member);    // 회원 수정(정보 변경시)
    void delete(Member member);    // 회원 탈퇴

    Member findById(Long id);    // 고유 아이디로 검색
    Optional<Member> findByLoginId(String loginId);    // 로그인 아이디로 검색
    List<Member> findAll();    // 모두 검색

}
