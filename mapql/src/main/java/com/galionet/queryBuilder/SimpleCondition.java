package com.galionet.queryBuilder;


import org.codehaus.jackson.annotate.JsonTypeName;


@JsonTypeName("simpleCondition") 
public class SimpleCondition extends Condition {
	
	private static final long serialVersionUID = -5484023844742279918L;
	private ComparisonOperator operator;
	private Operand operand1;
	private Operand operand2;
	
	public SimpleCondition(ComparisonOperator operator,
			Operand operand1,
			Operand operand2){
		name="simpleCondition";
		this.operator=operator;
		this.operand1=operand1;
		this.operand2=operand2;
	}
	public SimpleCondition(){
		
	}
	public ComparisonOperator getOperator() {
		return operator;
	}
	public void setOperator(ComparisonOperator operator) {
		this.operator = operator;
	}
	public Operand getOperand1() {
		return operand1;
	}
	public void setOperand1(Operand operand1) {
		this.operand1 = operand1;
	}
	public Operand getOperand2() {
		return operand2;
	}
	public void setOperand2(Operand operand2) {
		this.operand2 = operand2;
	}
	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.simpleConditionToSQL(this);
	}

}
