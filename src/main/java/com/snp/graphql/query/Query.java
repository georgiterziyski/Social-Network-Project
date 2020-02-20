package com.snp.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.snp.SocialNetworkProject.models.User;
import com.snp.SocialNetworkProject.repos.UserRepository;

@Component
public class Query implements GraphQLQueryResolver{

	@Autowired
	UserRepository userRepository;
	
	public List<User> allUserss() {
		return userRepository.findAll();
	}
}
