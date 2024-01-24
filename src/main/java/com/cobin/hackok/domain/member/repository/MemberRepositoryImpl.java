package com.cobin.hackok.domain.member.repository;

import com.cobin.hackok.domain.member.dto.Member;

import java.util.*;

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


    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
