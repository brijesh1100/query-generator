package com.hasura.query.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasura.query.controller.QueryController;

public class SQLGenerator {

	private static final Logger logger = LogManager.getLogger(QueryController.class);

	public static String generateSQL(String queryPayload) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			QueryRequest queryRequest = mapper.readValue(queryPayload, QueryRequest.class);

			// Access the deserialized data from queryRequest object
			logger.info("Collection: {}", queryRequest.getCollection());

			// Access fields within the query
			Query query = queryRequest.getQuery();

			// Build the SQL statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");

			// Add selected fields
			boolean firstField = true;
			for (Map.Entry<String, Field> entry : queryRequest.getQuery().getFields().entrySet()) {
				if (!firstField) {
					sql.append(", ");
				}
				if (entry.getValue() instanceof ColumnField) {
					ColumnField fld = (ColumnField) entry.getValue();
					sql.append(fld.getColumn());
				}
				firstField = false;
			}

			// Add FROM clause based on collection
			sql.append(" FROM ").append(queryRequest.getCollection());

			// JOIN Condition
//			if (!queryRequest.getCollectionRelationships().isEmpty()) {
//				for (Map.Entry<String, Relationship> entry : queryRequest.getCollectionRelationships().entrySet()) {
//					String joinType = getSqlJoinType(entry.getValue().getRelationshipType());
//					String targetCollection = entry.getValue().getTargetCollection();
//					Map<String, String> columnMapping = entry.getValue().getColumnMapping();
//					String joinCondition = buildJoinCondition(columnMapping, entry.getKey());
//
//					sql.append(" ").append(joinType).append(" JOIN ").append(targetCollection).append(" ON ")
//							.append(joinCondition);
//				}
//			}

			// Add WHERE clause (filter)
			Expression predicate = queryRequest.getQuery().getPredicate();
			if (predicate != null) {
				sql.append(" WHERE ");
				buildWhereClause(sql, predicate);
			}

			// Add GROUP BY clause
			HashMap<String, Aggregate> aggregates = queryRequest.getQuery().getAggregates();
			if (aggregates != null && !aggregates.isEmpty()) {
				sql.append(" GROUP BY ");
				boolean firstAgg = true;
				for (Map.Entry<String, Aggregate> entry : aggregates.entrySet()) {
					if (!firstAgg) {
						sql.append(", ");
					}
					sql.append(entry.getValue().getColumn());
					firstAgg = false;
				}
			}

			if (!queryRequest.getArguments().isEmpty()) {
				sql.append(" HAVING ");
				boolean firstArgument = true;
				for (Map.Entry<String, Argument> entry : queryRequest.getArguments().entrySet()) {
					if (!firstArgument) {
						sql.append(" AND ");
					}

					String argumentExpression = convertArgumentToSqlExpression(entry.getValue());
					sql.append(argumentExpression);
					firstArgument = false;
				}
			}

			// Add ORDER BY clause
			OrderBy orderBy = queryRequest.getQuery().getOrderBy();
			if (orderBy != null) {
				sql.append(" ORDER BY ");
				boolean firstElement = true;
				for (OrderByElement element : orderBy.getElements()) {
					if (!firstElement) {
						sql.append(", ");
					}
					String targetString = getTargetString(element.getTarget());
					if (targetString != null) {
						sql.append(targetString);
					}
					if (element.getOrderDirection() != null) {
						sql.append(" ");
						// Assuming a String enum for direction
						// (e.g., "ASC", "DESC")
						sql.append(element.getOrderDirection().toString());
					}
					firstElement = false;
				}
			}

			// Add LIMIT clause
			Integer limit = queryRequest.getQuery().getLimit();
			if (limit != null) {
				sql.append(" LIMIT ").append(limit);
			}

			// Add Offset clause
			Integer offset = queryRequest.getQuery().getOffset();
			if (limit != null) {
				sql.append(" OFFSET ").append(offset);
			}
//			Here we are returning the final SQL statement
			return sql.toString();
		} catch (Exception e) {
			logger.error("Error processing query request", e);
		}
		return "";
	}

	private static String getSqlJoinType(RelationshipType relationshipType) {
		switch (relationshipType) {
		case Object:
			return "INNER JOIN";
		case Array:
			return "LEFT JOIN"; // Common for array joins
		default:
			throw new UnsupportedOperationException("Unsupported relationship type");
		}
	}

	private static String buildJoinCondition(Map<String, String> columnMapping, String relationshipName) {
		StringBuilder condition = new StringBuilder();
		for (Map.Entry<String, String> mapping : columnMapping.entrySet()) {
			if (condition.length() > 0) {
				condition.append(" AND ");
			}
			condition.append(mapping.getKey()).append(" = ").append(relationshipName + "." + mapping.getValue());
		}
		return condition.toString();
	}

	private static String convertArgumentToSqlExpression(Argument argument) {
		if (argument instanceof VariableArgument) {
			VariableArgument varArg = (VariableArgument) argument;
			return varArg.getName(); // Use variable name in expression
		} else if (argument instanceof LiteralArgument) {
			LiteralArgument litArg = (LiteralArgument) argument;
			// Handle potential escaping or formatting for literal values
			return "'" + litArg.getValue().toString() + "'"; // Enclose in single quotes
		} else {
			// Handle other argument types if applicable
			throw new UnsupportedOperationException("Unsupported argument type");
		}
	}

	private static String getTargetString(OrderByTarget target) {
		switch (target.getType()) {
		case "column":
			ColumnTargetType columnTarget = (ColumnTargetType) target;
			return columnTarget.getName();
		case "single_column_aggregate":
			SingleColumnAggregateTarget aggTarget = (SingleColumnAggregateTarget) target;
			return aggTarget.getFunction() + "(" + aggTarget.getColumn() + ")";
		case "star_count_aggregate":
			// TODO Handle star count aggregate target (assuming "count(*)")
			return "count(*)";
		default:
			logger.warn("Unsupported OrderByTarget type: {}", target.getType());
			return null;
		}
	}

	/**
	 * This method needs to refactor and extends with other logical and compound
	 * operator This implementation is limited with request scope.
	 */
	private static String getOperetor(String operator) {
		if (operator.contains("lte")) {
			return "<=";
		} else if (operator.contains("gte")) {
			return ">=";
		} else if (operator.contains("like")) {
			return "like";
		} else if (operator.contains("in")) {
			return "in";
		} else {
			return "=";
		}
	}

	private static void buildWhereClause(StringBuilder sql, Expression expression) {
		switch (expression.getType()) {
//		This case can be improved since I have added few checks specific with given request scope.
		case "binary_comparison_operator":
			BinaryComparisonOperator binaryOp = (BinaryComparisonOperator) expression;
			sql.append(binaryOp.getColumn().getName()).append(" ").append(getOperetor(binaryOp.getOperator()))
					.append(" ");
			if (binaryOp.getValue().getType().equals("scalar") && binaryOp.getValue().getValue() instanceof List) {
				List<Integer> valueList = (List<Integer>) binaryOp.getValue().getValue();
				String valueString = valueList.stream().map(Object::toString).collect(Collectors.joining(","));
				sql.append("(").append(valueString).append(")");
			} else {
				String value = (String) binaryOp.getValue().getValue(); // Cast to String
				sql.append("'").append(value).append("' "); // Enclose in single quotes

			}
			// TODO Add logic for other value types (e.g., variables)
			break;
		case "unary_comparison_operator":
			// TODO Handle unary comparison logic (if applicable)
			break;
		case "and":
			AndExpression andExpr = (AndExpression) expression;
			sql.append("(");
			boolean firstAnd = true;
			for (Expression subExpr : andExpr.getExpressions()) {
				if (!firstAnd) {
					sql.append(" AND ");
				}
				buildWhereClause(sql, subExpr);
				firstAnd = false;
			}
			sql.append(")");
			break;
		case "or":
			OrExpression orExpr = (OrExpression) expression;
			sql.append("(");
			boolean firstOr = true;
			for (Expression subExpr : orExpr.getExpressions()) {
				if (!firstOr) {
					sql.append(" OR ");
				}
				buildWhereClause(sql, subExpr);
				firstOr = false;
			}
			sql.append(")");
			break;
		case "not":
			NotExpression notExpr = (NotExpression) expression;
			sql.append("NOT (");
			buildWhereClause(sql, notExpr.getExpression());
			sql.append(")");
			break;
		case "exists":
			ExistsExpression existsExpr = (ExistsExpression) expression;
			// TODO Handle EXISTS logic based on inCollection and predicate
			break;
		default:
			logger.warn("Unsupported expression type: " + expression.getType());
		}
	}
}
