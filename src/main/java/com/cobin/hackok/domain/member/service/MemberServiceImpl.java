package com.cobin.hackok.domain.member.service;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
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

    @Override
    public Optional<Member> findPasswordByLoginIdAndName(String loginId, String name) {
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        if(findMember.isPresent()){
            String findName = findMember.get().getName();
            if(findName.equals(name)) return findMember;
            return Optional.empty();
        }
        return findMember;
    }
}
