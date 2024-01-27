package com.cobin.hackok.domain;

import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        Member member = new Member();
        member.setLoginId("aaaa@aaa.com");
        member.setPassword("1111");
        member.setName("테스터");
        memberRepository.save(member);
    }

}
