package com.snp.SocialNetworkProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snp.SocialNetworkProject.models.User;
import com.snp.SocialNetworkProject.repos.UserRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllUsersDataFetcher implements DataFetcher<List<User>> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> get(DataFetchingEnvironment environment) throws Exception {
		return userRepository.findAll();
	}

}
