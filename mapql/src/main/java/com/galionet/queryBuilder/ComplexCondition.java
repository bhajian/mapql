package com.galionet.queryBuilder;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("complexCondition") 
public class ComplexCondition extends Condition {

	private static final long serialVersionUID = 2796975798865794778L;
	private LogicalOperator operator;
	private List<Condition> conditions;
	
	public ComplexCondition(){
		name="complexCondition";
		conditions=new ArrayList<Condition>();
	}
	
	public void addCondition(Condition condition){
		this.conditions.add(condition);
	}
	
	public LogicalOperator getOperator() {
		return operator;
	}
	public void setOperator(LogicalOperator operator) {
		this.operator = operator;
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.complexConditionToSQL(this);
	}
	
}
