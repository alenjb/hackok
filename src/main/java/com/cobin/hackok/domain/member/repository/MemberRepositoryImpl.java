package com.cobin.hackok.domain.member.repository;

import com.cobin.hackok.domain.member.dto.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class MemberRepositoryImpl implements MemberRepository{
    private static final Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L;//static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Member update(Member member) {
        Member findMember = store.get(member.getId());
        store.replace(member.getId(), findMember, member);
        return member;
    }

    @Override
    public void delete(Member member) {
        store.remove(member.getId());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 테스트를 위한 기능
    @Override
    public void clean(){
        store.clear();
    }
}
