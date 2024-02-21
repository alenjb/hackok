package com.cobin.hackok.domain;

import com.cobin.hackok.domain.member.domain.MemberGrade;
import com.cobin.hackok.domain.member.dto.Member;
import com.cobin.hackok.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
        Member member = new Member(new ObjectId(), "aaaa@aaa.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        memberRepository.save(member);
    }

}
