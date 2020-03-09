package com.snp.SocialNetworkProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snp.SocialNetworkProject.models.User;
import com.snp.SocialNetworkProject.repos.UserRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class GetUserDataFetcher implements DataFetcher<User> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User get(DataFetchingEnvironment environment) throws Exception {
		String email = environment.getArgument("email");
		return userRepository.findByEmail(email);
	}

}
