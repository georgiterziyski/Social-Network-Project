package com.snp.social_network_project.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserInput {
	final String username;
	final String email;
	final String password;
}
