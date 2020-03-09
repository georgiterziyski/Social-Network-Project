/**
 * 
 */
package com.snp.SocialNetworkProject.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

/**
 * @author Terziyski
 *
 */
@Service
public class GraphQLService {

	@Value("classpath:graphql/schema.graphqls")
	Resource resource;
	
	private GraphQL graphQL;
	
	@Autowired
	private AllUsersDataFetcher allUsersDataFetcher;
	
	@Autowired
	private GetUserDataFetcher getUserDataFetcher;
	
	@PostConstruct 
	private void loadSchema() throws IOException {
		File schemaFile = resource.getFile();
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring(); 
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring
						.dataFetcher("allUsers", allUsersDataFetcher)
						.dataFetcher("getUser", getUserDataFetcher))
				.build();
	}

	@Bean
	public GraphQL getGraphQL() {
		return graphQL;
	}
}
