package com.cobin.hackok.domain.summary.repository;

import com.cobin.hackok.domain.summary.dto.Summary;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository {

    // 1. 조회
    Summary read(Summary summary);

    // 2. 저장
    Boolean save(Summary summary);

    // 3. 수정
    Summary update(Long oldId, Summary summary);

    // 4. 삭제
    Boolean delete(Summary summary);

    // 5. 초기화 - 임시 저장소 테스트 용
    void clearStore();
}
