package com.hasura.query.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(name = "and", value = AndExpression.class),
		@JsonSubTypes.Type(name = "or", value = OrExpression.class),
		@JsonSubTypes.Type(name = "not", value = NotExpression.class),
		@JsonSubTypes.Type(name = "unary_comparison_operator", value = UnaryComparisonOperator.class),
		@JsonSubTypes.Type(name = "binary_comparison_operator", value = BinaryComparisonOperator.class),
		@JsonSubTypes.Type(name = "exists", value = ExistsExpression.class), })
public abstract class Expression {

	public abstract String getType(); // Abstract method to be implemented by subclasses
}

// And Expression
class AndExpression extends Expression {

	@JsonProperty("expressions")
	private List<Expression> expressions;

	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public String getType() {
		return "and";
	}
}

// Or Expression
class OrExpression extends Expression {

	@JsonProperty("expressions")
	private List<Expression> expressions;

	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public String getType() {
		return "or";
	}
}

// Not Expression
class NotExpression extends Expression {

	@JsonProperty("expression")
	private Expression expression;

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String getType() {
		return "not";
	}
}

// Unary Comparison Operator
class UnaryComparisonOperatorType extends Expression {

	@JsonProperty("column")
	private ComparisonTarget column;

	@JsonProperty("operator")
	private UnaryComparisonOperator operator;

	public ComparisonTarget getColumn() {
		return column;
	}

	public void setColumn(ComparisonTarget column) {
		this.column = column;
	}

	public UnaryComparisonOperator getOperator() {
		return operator;
	}

	public void setOperator(UnaryComparisonOperator operator) {
		this.operator = operator;
	}

	@Override
	public String getType() {
		return "unary_comparison_operator";
	}
}

// Binary Comparison Operator
class BinaryComparisonOperator extends Expression {

	@JsonProperty("column")
	private ComparisonTarget column;

	@JsonProperty("operator")
	private String operator;

	@JsonProperty("value")
	private ComparisonValue value;

	public ComparisonTarget getColumn() {
		return column;
	}

	public void setColumn(ComparisonTarget column) {
		this.column = column;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public ComparisonValue getValue() {
		return value;
	}

	public void setValue(ComparisonValue value) {
		this.value = value;
	}

	@Override
	public String getType() {
		return "binary_comparison_operator";
	}
}

class ExistsExpression extends Expression {

	@JsonProperty("in_collection")
	private ExistsInCollection inCollection;

	@JsonProperty("predicate")
	private Expression predicate;

	public Expression getPredicate() {
		return predicate;
	}

	public void setPredicate(Expression predicate) {
		this.predicate = predicate;
	}

	public ExistsInCollection getInCollection() {
		return inCollection;
	}

	public void setInCollection(ExistsInCollection inCollection) {
		this.inCollection = inCollection;
	}

	@Override
	public String getType() {
		return "exists";
	}
}
