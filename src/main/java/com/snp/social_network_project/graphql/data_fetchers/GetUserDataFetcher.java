package com.snp.social_network_project.graphql.data_fetchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snp.social_network_project.models.User;
import com.snp.social_network_project.repos.UserRepository;

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
