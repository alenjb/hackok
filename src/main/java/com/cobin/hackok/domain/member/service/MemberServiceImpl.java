package com.cobin.hackok.domain.member.service;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    MemberRepository memberRepository;

    @Override
    public boolean signup(Member member) {
        Member newMember = memberRepository.save(member);
        return newMember != null;
    }

    @Override
    public Member login(Member member) {
        return null;
    }
}
