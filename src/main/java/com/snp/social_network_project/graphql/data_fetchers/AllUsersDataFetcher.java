package com.snp.social_network_project.graphql.data_fetchers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snp.social_network_project.models.User;
import com.snp.social_network_project.repos.UserRepository;

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
