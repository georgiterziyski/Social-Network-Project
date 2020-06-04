package com.snp.social_network_project.graphql.mutation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.snp.social_network_project.graphql.input.CreateUserInput;
import com.snp.social_network_project.models.User;
import com.snp.social_network_project.repos.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver{
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public User createUser(final CreateUserInput input) {
		final User newUser = new User(input.getUsername(),
									  input.getPassword(),
									  input.getEmail(),
									  input.getFirstName(),
									  input.getLastName(),
									  "");
		return userRepository.saveAndFlush(newUser);
	}

}
