package com.galionet.queryBuilder;

public class JoinClause extends Clause{

	private static final long serialVersionUID = 1570533029707585457L;

	public enum JoinType{
		INNDER_JOIN,
		LEFT_OUTER_JOIN,
		RIGHT_OUTER_JOIN
	}
	
	private JoinType joinType;
	private Table table;
	private ComplexCondition condition;
	
	public ComplexCondition getCondition() {
		return condition;
	}
	public void setCondition(ComplexCondition condition) {
		this.condition = condition;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public JoinType getJoinType() {
		return joinType;
	}
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	public void addAndCondition(ComparisonOperator operator, Operand operand1, Operand operand2){
		if(condition == null){
			condition=new ComplexCondition();
			condition.setOperator(LogicalOperator.AND);
		}
		SimpleCondition simpleCondition=new SimpleCondition(operator, operand1, operand2);
		condition.addCondition(simpleCondition);
	}
	
	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.joinClauseToSQL(this);
	}
}
