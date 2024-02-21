package com.cobin.hackok;

import com.cobin.hackok.domain.member.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MemberRepository.class)
public class HackokApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackokApplication.class, args);
	}

}
