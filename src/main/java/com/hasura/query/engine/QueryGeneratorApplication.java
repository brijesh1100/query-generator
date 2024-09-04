package com.hasura.query.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hasura.query.controller.QueryController;

/**
 * This class will boot the Application and you can hit the query
 * {@link QueryController}
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.hasura.query")
public class QueryGeneratorApplication {
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		SpringApplication.run(QueryGeneratorApplication.class, args);
	}

}
