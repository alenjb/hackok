package com.cobin.hackok.domain.summary.repository;

import com.cobin.hackok.domain.summary.dto.Summary;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository {

    // 1. 조회
    Boolean read(Summary summary);

    // 2. 저장
    Boolean save(Summary summary);

    // 3. 수정
    Boolean update(Summary summary);

    // 4. 삭제
    Boolean delete(Summary summary);
}
