package com.snp.social_network_project.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserInput {
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
}
