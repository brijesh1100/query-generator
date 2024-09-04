package com.hasura.query.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.hasura.query.model.QueryResponse;
import com.hasura.query.model.RowFieldValue;
import com.hasura.query.model.RowSet;

@Component
public class QueryExecutor {

	public QueryResponse executeQuery(String query) throws SQLException {
		
		DataSource dataSource = DataSourceFactory.NEW_INSTANCE().withUsername("root").withPassword("password")
				.withDriverClassName("com.mysql.cj.jdbc.Driver")
				.withJdbcUrl("jdbc:mysql://localhost:3306/chinook?useCursorFetch=true").withDatabase("chinook").build();
		List<RowSet> rowSets = new ArrayList<>();
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection.isClosed());

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			rowSets.add(mapResultSet(resultSet));
		} finally {
			connection.close(); // Ensure connection is closed even on exceptions
		}

		return new QueryResponse(rowSets);
	}

	private RowSet mapResultSet(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();

		List<Map<String, RowFieldValue>> rows = new ArrayList<>();
		while (resultSet.next()) {
			Map<String, RowFieldValue> row = new HashMap<>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				Object value = resultSet.getObject(i);
				row.put(columnName, new RowFieldValue(value));
			}
			rows.add(row);
		}

		return new RowSet(null, rows); // Assuming no aggregates in this example
	}
}
