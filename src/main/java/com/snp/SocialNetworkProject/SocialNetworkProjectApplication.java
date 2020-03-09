package com.snp.SocialNetworkProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.snp.SocialNetworkProject.repos.UserRepository;
import com.snp.graphql.query.Query;

@SpringBootApplication
public class SocialNetworkProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkProjectApplication.class, args);
	}
}
