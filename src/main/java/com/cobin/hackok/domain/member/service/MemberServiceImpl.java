package com.cobin.hackok.domain.member.service;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean signup(Member member) {
        Member newMember = memberRepository.save(member);
        return newMember != null;
    }

    @Override
    public Member login(Member member) {
        /*
         *로그인에 실패하면 null을 반환
         */
        return memberRepository.findByLoginId(member.getLoginId())
                .filter(m -> m.getPassword().equals(member.getPassword()))
                .orElse(null);
    }
}
