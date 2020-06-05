package com.snp.graphql.mutation;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.snp.Security.UserPrincipal;
import com.snp.Security.JWT.JwtProperties;
import com.snp.graphql.input.CreateUserInput;
import com.snp.graphql.input.LoginPayload;
import com.snp.graphql.input.UserCredentialsInput;
import com.snp.models.User;
import com.snp.repos.UserRepository;

import graphql.GraphQLException;
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
	@Transactional
	public LoginPayload login(final UserCredentialsInput auth) throws IllegalAccessException{
		final User user = userRepository.findByEmail(auth.getEmail());
		
        // Create JWT Token
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));

	    return new LoginPayload(token, user);

	}

}
