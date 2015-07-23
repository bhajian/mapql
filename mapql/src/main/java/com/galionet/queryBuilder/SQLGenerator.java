package com.galionet.queryBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class SQLGenerator implements Serializable {

	private static final long serialVersionUID = 8358687696316233590L;
	protected ArrayList<LiteralValue> parameters;
	
		
	public abstract String fieldOperandToSQL(FieldOperand operand) throws ArgumentNullException ;
	public abstract String literalOperandToSQL(LiteralOperand operand) throws ArgumentNullException ;
	public abstract String setOfLiteralOperandToSQL(SetOfLiteralOperand operand) throws ArgumentNullException ;
	public abstract String complexConditionToSQL(ComplexCondition condition) throws ArgumentNullException ;
	public abstract String simpleConditionToSQL(SimpleCondition condition) throws ArgumentNullException ;
	public abstract String projectionToSQL(Projection projection) throws ArgumentNullException ;
	public abstract String fromClauseToSQL(FromClause fromClause) throws ArgumentNullException ;
	public abstract String joinClauseToSQL(JoinClause fromClause) throws ArgumentNullException ;
	public abstract ParsedQuery parseQuery(Query query) throws ArgumentNullException ;
	public abstract String subQueryToSQL(Query query) throws ArgumentNullException;
}
