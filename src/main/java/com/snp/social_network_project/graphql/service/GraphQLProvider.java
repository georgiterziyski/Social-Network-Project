/**
 * 
 */
package com.snp.social_network_project.graphql.service;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.snp.social_network_project.graphql.data_fetchers.AllUsersDataFetcher;
import com.snp.social_network_project.graphql.data_fetchers.GetUserDataFetcher;

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
@Component
public class GraphQLProvider {

	@Value("classpath:graphql/schema.graphqls")
	Resource resource;
	
	private GraphQL graphQL;
	
	@Bean
	public GraphQL getGraphQL() {
		return graphQL;
	}
	
	@Autowired
	private AllUsersDataFetcher allUsersDataFetcher;
	
	@Autowired
	private GetUserDataFetcher getUserDataFetcher;
	
	@PostConstruct 
	private void loadSchema() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
		//File schemaFile = resource.getFile();
		//TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		//RuntimeWiring wiring = buildRuntimeWiring(); 
		//GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
		//graphQL = GraphQL.newGraphQL(schema).build();
	}

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
      }
    
	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring
						.dataFetcher("allUsers", allUsersDataFetcher)
						.dataFetcher("getUser", getUserDataFetcher))
				.build();
	}
}
