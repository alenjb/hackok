package com.cobin.hackok;

import com.cobin.hackok.domain.member.repository.MemberRepository;
import com.cobin.hackok.domain.summary.repository.SummaryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {MemberRepository.class, SummaryRepository.class})
public class HackokApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackokApplication.class, args);
	}

}
