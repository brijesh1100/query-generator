package com.hasura.query.controller;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasura.query.engine.QueryExecutor;
import com.hasura.query.model.QueryResponse;
import com.hasura.query.model.SQLGenerator;

@RestController
@RequestMapping("/api")
public class QueryController {

	private static final Logger logger = LogManager.getLogger(QueryController.class);

	@Autowired
	private QueryExecutor queryExecutor;

	/**
	 * This method accept JSON request and parse into QueryRequest.
	 * after deserialization into QueryRequest it will generate the SQL which is getting logged
	 * Then it will execute the SQL query and return the QueryResponse which return as JSON serialization. 
	 * */
	@PostMapping(path = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String execute(@RequestBody String requestBody) throws JsonProcessingException {

		String sqlQuery = SQLGenerator.generateSQL(requestBody);
		logger.info("Generated SQL QUERY: {}", sqlQuery);
		QueryResponse response = null;
		try {
			response = queryExecutor.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(response);

	}

}
