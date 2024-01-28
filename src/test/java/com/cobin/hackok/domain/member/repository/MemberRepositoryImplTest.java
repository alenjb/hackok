package com.cobin.hackok.domain.member.repository;

import com.cobin.hackok.domain.member.domain.MemberGrade;
import com.cobin.hackok.domain.member.dto.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
class MemberRepositoryImplTest {
    MemberRepository repository = new MemberRepositoryImpl();


    @BeforeEach
    void beforeEach(){
        Member member1 = new Member(1L, "aaaa@aaa.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        Member member2 = new Member(2L, "bbbb@aaa.com","테스터2", "2222", "24/03/24", "01/02/24", MemberGrade.PREMIUM);
        repository.save(member1);
        repository.save(member2);
    }
    @AfterEach
    void afterEach(){
        repository.clean();
    }

    @Test
    void save() {
        //given
        Member member3 = new Member(3L, "aaaa@aaa.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        Member member4 = new Member(4L, "bbbb@aaa.com","테스터2", "2222", "24/03/24", "01/02/24", MemberGrade.PREMIUM);

        //when
        repository.save(member3);
        repository.save(member4);

        //then
        assertThat(repository.findById(member3.getId())).isEqualTo(Optional.of(member3));
        assertThat(repository.findById(member4.getId())).isEqualTo(Optional.of(member4));
    }

    @Test
    void update() {
        //given
        Member findMember = repository.findById(1L).get();
        Member updateMember = new Member(1L, "update@aaa.com", "테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);

        //when
        repository.update(findMember, updateMember);

        //then
        assertThat(repository.findByLoginId("update@aaa.com")).isEqualTo((Optional.of(updateMember)));
    }

    @Test
    void delete() {
        //given
        int beforeDeleteSize = repository.findAll().size();
        // 이미 레포지토리에 있는 멤버(beforeEach에서 생성)
        Member member = new Member(2L, "bbbb@aaa.com","테스터2", "2222", "24/03/24", "01/02/24", MemberGrade.PREMIUM);

        //when
        repository.delete(member);

        //then
        assertThat(repository.findAll().size()).isEqualTo(1L);
    }

    @Test
    void findById() {
        //given
        long id = ((MemberRepositoryImpl) repository).getSequence() + 1; // 현재 저장 시 부여되는 id 값
        Member member3 = new Member(id, "member3@naver.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        repository.save(member3);

        //when
        Optional<Member> findMember = repository.findById(id);

        //then
        assertThat(findMember).isEqualTo(Optional.of(member3));
    }

    @Test
    void findByLoginId() {
        //given
        Member member3 = new Member(3L, "111@aaa.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        repository.save(member3);

        //when
        Optional<Member> findMember = repository.findByLoginId("111@aaa.com");

        //then
        assertThat(findMember).isEqualTo(Optional.of(member3));

    }

    @Test
    void findAll() {
        //given
        repository.clean();
        Member member3 = new Member(3L, "aaaa@aaa.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        Member member4 = new Member(4L, "aaaa@aaa.com","테스터1", "1111", "26/08/24", "01/01/24", MemberGrade.BASIC);
        /**
         * member1과 2는 beforeEach에서 이미 저장된 상태임
         */
        repository.save(member3);
        repository.save(member4);

        //when
        List<Member> memberList = repository.findAll();
        //then
        assertThat(memberList).containsOnly(member3,member4);

    }
}