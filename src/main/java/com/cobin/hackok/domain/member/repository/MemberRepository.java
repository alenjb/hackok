package com.cobin.hackok.domain.member.repository;

import com.cobin.hackok.domain.member.dto.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository extends MongoRepository<Member, ObjectId> {

    Member save(Member member);    // 회원 저장
    void delete(Member member);    // 회원 탈퇴

    Optional<Member> findById(ObjectId id);    // 고유 아이디로 검색
    Optional<Member> findByLoginId(String loginId);    // 로그인 아이디로 검색
    List<Member> findAll();    // 모두 검색

    Member updateById(ObjectId id, Member member); // 아이디로 정보 수정
    void deleteAll(); // 레포지토리 내의 값 모두 제거

}
