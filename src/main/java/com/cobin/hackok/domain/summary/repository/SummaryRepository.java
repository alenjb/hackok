package com.cobin.hackok.domain.summary.repository;

import com.cobin.hackok.domain.summary.dto.Summary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends MongoRepository<Summary, ObjectId> {

    // 1-1. 핵콕 1개 조회
    Summary findSummaryByUserId(String userId);

    // 1-2 사용자의 모든 핵콕 조회
    List<Summary> findSummariesByUserIdOrderByIdDesc(String userId);

    // 2. 저장 및 수정
    Summary save(Summary summary);

    // 3. 삭제
    void delete(Summary summary);

    // 4. 초기화 - 임시 저장소 테스트 용
    void deleteAll();
}
