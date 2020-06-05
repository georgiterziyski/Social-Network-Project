package com.snp.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.snp.models.User;
import com.snp.repos.UserRepository;

@Component
public class Query implements GraphQLQueryResolver{

	@Autowired
	UserRepository userRepository;
	
	public List<User> allUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(final String email) {
		return userRepository.findByEmail(email);
	}
}
