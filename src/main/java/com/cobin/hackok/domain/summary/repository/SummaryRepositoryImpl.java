package com.cobin.hackok.domain.summary.repository;

import com.cobin.hackok.domain.summary.dto.Summary;

import java.util.HashMap;
import java.util.Map;

public class SummaryRepositoryImpl implements SummaryRepository{

    // 데이터 베이스 연결 전 사용할 임시 저장소
    private Map<Long, Summary> repo = new HashMap<>();

    // 1. 조회
    @Override
    public Summary read(Summary summary) {
        return repo.get(summary.getId());
    }

    // 2. 저장
    @Override
    public Boolean save(Summary summary) {
        repo.put(summary.getId(), summary);
        return true;
    }

    // 3. 수정
    @Override
    public Summary update(Summary summary) {
        repo.replace(summary.getId(), repo.get(summary.getId()), summary);
        return repo.get(summary.getId());
    }

    // 4. 삭제
    @Override
    public Boolean delete(Summary summary) {
        repo.remove(summary.getId());
        return true;
    }
}
