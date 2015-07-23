package com.galionet.queryBuilder;


import java.util.ArrayList;
import java.util.List;
import com.citi.cept.cps.util.DataType;

public class GemfireSQLGenerator extends SQLGenerator {

	private static final long serialVersionUID = -7357433733699732062L;
	private String tablePrefixExtension;

	public GemfireSQLGenerator(String tablePrefix){
		this.tablePrefixExtension=tablePrefix;
	}

	@Override
	public String fieldOperandToSQL(FieldOperand operand) throws ArgumentNullException{
		if(operand == null)
			throw new ArgumentNullException("");
		StringBuilder sb=new StringBuilder();
		Field field=operand.getField();
		if(field != null){
			sb.append(field.getFullPathName(operand.getTable()));
			return sb.toString();
		}
		return "";
	}

	@Override
	public String literalOperandToSQL(LiteralOperand operand) throws ArgumentNullException{
		if(operand == null)
			throw new ArgumentNullException("");
		StringBuilder sb=new StringBuilder();
		LiteralValue literalValue=operand.getLiteralValue();
		if(literalValue != null){
			if(literalValue.getType() != DataType.NULL){
				sb.append("$" + Integer.toString(parameters.size()+1));
				parameters.add(literalValue);
			}else{
				sb.append(literalValue.getValue());
			}
			return sb.toString();
		}
		return "NULL";
	}

	@Override
	public String setOfLiteralOperandToSQL(SetOfLiteralOperand operand) throws ArgumentNullException {
		if(operand == null)
			throw new ArgumentNullException("");
		StringBuilder sb=new StringBuilder();
		List<LiteralValue> valueList=operand.getLiteralValueSet();
		sb.append(" SET( ");
		int valuesCount=0;
		for(LiteralValue lvalue:valueList){
			if(valuesCount>0){
				sb.append(" , ");
			}
			if(lvalue.getType() == DataType.STRING){
				sb.append("'");
			}
			sb.append(lvalue.getValue());
			if(lvalue.getType() == DataType.STRING){
				sb.append("'");
			}
			valuesCount++;
		}
		sb.append(" ) ");
		return sb.toString();
	}

	@Override
	public String complexConditionToSQL(ComplexCondition condition) throws ArgumentNullException{
		if(condition == null)
			throw new ArgumentNullException("");
		
		StringBuilder sb=new StringBuilder();
		if(condition.getConditions().size() > 1){
			sb.append("( ");
		}
		int cnt=0;
		for(Condition con:condition.getConditions()){
			if(cnt>0){
				sb.append(condition.getOperator().getStringValue());
			}
			cnt++;
			sb.append(" "+con.toSQL(this)+" ");
		}
		if(condition.getConditions().size() > 1){
			sb.append(" )");
		}
		return sb.toString();
	}

	@Override
	public String simpleConditionToSQL(SimpleCondition condition) throws ArgumentNullException{
		if(condition == null)
			throw new ArgumentNullException("");
		StringBuilder sb=new StringBuilder();
		sb.append("( ");
		sb.append(condition.getOperand1().toSQL(this));
		sb.append(" "+condition.getOperator().getStringValue()+" ");
		sb.append(condition.getOperand2().toSQL(this));
		sb.append(" )");
		return sb.toString();
	}

	@Override
	public String projectionToSQL(Projection projection) throws ArgumentNullException {
		if(projection == null)
			throw new ArgumentNullException("");
		StringBuilder sb=new StringBuilder();
		
		List<Field> fieldList=projection.getFields();
		for(Field field:fieldList){
			if(sb.length()>0){
				sb.append(" , ");
			}
			sb.append(field.getFullPathName());
		}
		return sb.toString();
	}

	@Override
	public String fromClauseToSQL(FromClause fromClause) throws ArgumentNullException {
		if(fromClause == null)
			throw new ArgumentNullException("");
		StringBuilder sb=new StringBuilder();
		sb.append(" /"+tablePrefixExtension);
		sb.append(fromClause.getTable().getTableNameWithAlias());
		return sb.toString();
	}
	
	@Override
	public String joinClauseToSQL(JoinClause joinClause)
			throws ArgumentNullException {
		StringBuilder sb=new StringBuilder();
		sb.append(" /"+tablePrefixExtension);
		sb.append(joinClause.getTable().getTableNameWithAlias());
		return sb.toString();
	}
	
	@Override
	public String subQueryToSQL(Query subQuery) throws ArgumentNullException{
		GemfireSQLGenerator queryGenerator=new GemfireSQLGenerator("CPS_CACHE_");
		ParsedQuery parsedQuery=queryGenerator.parseQuery(subQuery);
		return parsedQuery.getSqlQuery();
	}
	

	@Override
	public ParsedQuery parseQuery(Query query) throws ArgumentNullException {
		if(query == null)
			throw new ArgumentNullException("");
		parameters=new ArrayList<LiteralValue>();
		ParsedQuery parsedQuery=new ParsedQuery();
		parsedQuery.setParameters(parameters);
		StringBuilder sb=new StringBuilder();
		Projection projection=query.getProjection();
		FromClause fromClause=query.getFromClause();
		Condition condition=query.getCondition();
		if(projection == null)
			throw new ArgumentNullException("projection is null");
		if(fromClause == null)
			throw new ArgumentNullException("from clause is null");
		sb.append("SELECT ");
		if(query.isDistinct()){
			sb.append(" DISTINCT ");
		}
		sb.append(query.getProjection().toSQL(this));
		sb.append(" FROM ");
		ComplexCondition joinCondition=null;
		if(query.getSubQuery() != null){
			sb.append(" ( ");
			sb.append(subQueryToSQL(query.getSubQuery()));
			sb.append(" ) sub_tbl ");
		}else{
			sb.append(query.getFromClause().toSQL(this));
			if(query.getJoins() != null && !query.getJoins().isEmpty()){
				joinCondition=new ComplexCondition();
				joinCondition.setOperator(LogicalOperator.AND);
				if(condition != null){
					joinCondition.addCondition(condition);
				}
				for(JoinClause jc: query.getJoins()){
					sb.append(" , ");
					sb.append(jc.toSQL(this));
					joinCondition.addCondition(jc.getCondition());
				}
			}
			
		}
		if(condition != null || joinCondition != null){
			sb.append(" WHERE ");
			if(joinCondition != null){
				sb.append(joinCondition.toSQL(this));
			}else{
				sb.append(condition.toSQL(this));
			}
		}
		this.parameters=null;
		parsedQuery.setSqlQuery(sb.toString());
		return parsedQuery;
	}
}
