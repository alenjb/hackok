package com.cobin.hackok.domain.summary.repository;

import com.cobin.hackok.domain.summary.dto.Summary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SummaryRepositoryTest {

    SummaryRepository repository = new SummaryRepositoryImpl();

    @AfterEach
    public void afterEach(){repository.clearStore();}

    @BeforeEach
    public void beforeEach(){
        repository.save(new Summary( 1L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText"));
        repository.save(new Summary( 2L,"userB", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText"));
    }

    @Test
    void read() {
        //given
        Summary summary1 = new Summary( 3L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText");
        repository.save(summary1);

        //when
            // 1. 찾는 객체가 존재하는 경우 - 성공
        Summary readResult1 = repository.read(summary1);
            // 2. 찾는 객체가 존재하지 않는 경우 - 실패
        Summary readResult2 = repository.read(new Summary( 4L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText"));

        //then
        assertEquals(summary1, readResult1);
        assertThat(readResult2).isNull();
    }

    @Test
    void save() {
        //given
            // 1. 모든 필드가 채워진 경우 - 성공
        Summary summary1 = new Summary( 3L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText");

            // 2. id 필드가 비워진 경우 - 성공
        Summary summary2 = new Summary( 4L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText");

        //when
        Boolean saveResult1 = repository.save(summary1);
        Boolean saveResult2 = repository.save(summary2);

        //then
        assertTrue(saveResult1);
        assertTrue(saveResult2);
    }

    @Test
    void update() {
        //given
        // 1. 수정하려는 객체가 존재하는 경우 - 성공
        Summary summary1 = new Summary( 3L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText");

        // 2. 수정하려는 객체가 존재하지 않는 - 실패
        Summary summary2 = new Summary( 4L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText");

        //when
        Summary updateResult1 = repository.update(1L, summary1);
        Summary updateResult2 = repository.update(100L, summary1);

        //then
        assertThat(updateResult1).isEqualTo(updateResult1);
        assertThat(updateResult2).isNotEqualTo(summary2);
    }

    @Test
    void delete() {
        //given
        Summary summary1 = new Summary( 3L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText");
        repository.save(summary1);

        //when
        // 1. 삭제하는 객체가 존재하는 경우 - 성공
        Boolean deleteResult1 = repository.delete(summary1);
        // 2. 삭제하는 객체가 존재하지 않는 경우 - 실패
        Boolean deleteResult2 = repository.delete(new Summary( 4L,"userA", "rawText", "title", List.of("keyword1", "keyword2"), "summaryText"));

        //then
        assertTrue(deleteResult1);
        assertFalse(deleteResult2);

    }
}