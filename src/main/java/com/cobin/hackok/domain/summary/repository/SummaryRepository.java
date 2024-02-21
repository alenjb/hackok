package com.cobin.hackok.domain.summary.repository;

import com.cobin.hackok.domain.summary.dto.Summary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository extends MongoRepository<Summary, ObjectId> {

    // 1. 조회
    Summary findSummariesByUserId(String userId);

    // 2. 저장 및 수정
    Summary save(Summary summary);

    // 3. 삭제
    void delete(Summary summary);

    // 4. 초기화 - 임시 저장소 테스트 용
    void deleteAll();
}
