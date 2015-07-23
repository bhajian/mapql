package com.galionet.queryBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.citi.cept.cps.util.DataType;

public class SimpleQueryFactory implements Serializable {
	
	private static final long serialVersionUID = -3760140906370268910L;
	private Query simpleQuery;
	
	public SimpleQueryFactory(){
		simpleQuery=new Query();
		simpleQuery.setFromClause(new FromClause());
	}
	
	public SimpleQueryFactory(Table table){
		simpleQuery=new Query();
		simpleQuery.setFromClause(new FromClause());
		simpleQuery.getFromClause().setTable(table);
		simpleQuery.setProjection(new Projection());
	}
	
	public SimpleQueryFactory(String tableName) throws ArgumentNullException{
		simpleQuery=new Query();
		setTableName(tableName, tableName + "_alias");
		simpleQuery.setProjection(new Projection());
	}
	
	public void setDistinct(boolean distincted){
		simpleQuery.setDistinct(distincted);
	}
	
	public SimpleQueryFactory(String tableName, String tableAlias) throws ArgumentNullException{
		simpleQuery=new Query();
		setTableName(tableName, tableAlias);
		simpleQuery.setProjection(new Projection());
	}
	
	public void setTableName(String tableName, String tableAlias) throws ArgumentNullException{
		if(tableName == null)
			throw new ArgumentNullException("The table name was null.");
		simpleQuery.setFromClause(new FromClause(tableName, tableAlias));
	}
	
	public Field addField(Field field){
		simpleQuery.getProjection().addField(field);
		return field;
	}
	
	public Field addField(String fieldName){
		Field field=simpleQuery.getFromClause().getTable().addField(fieldName, null);
		simpleQuery.getProjection().addField(field);
		return field;
	}
	
	public Field addField(String fieldName, String alias){
		Field field=simpleQuery.getFromClause().getTable().addField(fieldName, alias);
		simpleQuery.getProjection().addField(field);
		return field;
	}
	
	public void addCondition(Field field, LiteralValue literalValue) throws ArgumentNullException{
		addCondition(field, literalValue, ComparisonOperator.EQUALS);
	}
	
	public void addCondition(Field field, LiteralValue literalValue, ComparisonOperator operator) throws ArgumentNullException{
		if(field == null)
			throw new ArgumentNullException("the field was null");
		if(literalValue == null)
			throw new ArgumentNullException("the literalValue was null");
		
		ComplexCondition condition=getCondition();
		condition.addCondition(
				new SimpleCondition(operator, 
				new FieldOperand(simpleQuery.getTable(field), field), 
				new LiteralOperand(literalValue)));
	}
	
	public void addCondition(Field field, List<LiteralValue> literalValueList) throws ArgumentNullException{
		addCondition(field, literalValueList, ComparisonOperator.IN);
	}
	
	public void addCondition(Field field, List<LiteralValue> literalValueList, ComparisonOperator operator) throws ArgumentNullException{
		if(field == null)
			throw new ArgumentNullException("the field was null");
		if(literalValueList == null)
			throw new ArgumentNullException("the literalValue was null");
		
		ComplexCondition condition=getCondition();
		condition.addCondition(
				new SimpleCondition(operator, 
				new FieldOperand(simpleQuery.getTable(field), field), 
				new SetOfLiteralOperand(literalValueList)));
	}
	
	public void addCondition(String fieldName, Object value) throws ArgumentNullException{
		if(fieldName == null)
			throw new ArgumentNullException("the field name was null");
		FromClause fromCaluse = simpleQuery.getFromClause();
		if(fromCaluse == null)
			throw new ArgumentNullException("the from clause was null");
		Table table=fromCaluse.getTable();
		if(table == null)
			throw new ArgumentNullException("the table was null");
		Field field=simpleQuery.getFromClause().getTable().findField(fieldName);
		if(field == null){
			field=table.addField(fieldName, null);
		}
		if(value instanceof String){
			addCondition(field, new LiteralValue(value, DataType.STRING));
		}else if(value instanceof Float){
			addCondition(field, new LiteralValue(value, DataType.FLOAT));
		}else if(value instanceof Double){
			addCondition(field, new LiteralValue(value, DataType.DOUBLE));
		}else if(value instanceof Integer){
			addCondition(field, new LiteralValue(value, DataType.INT));
		}else if(value instanceof Long){
			addCondition(field, new LiteralValue(value, DataType.LONG));
		}else if(value instanceof Short){
			addCondition(field, new LiteralValue(value, DataType.SHORT));
		}else if(value instanceof Boolean){
			addCondition(field, new LiteralValue(value, DataType.BOOLEAN));
		}else if(value instanceof Byte){
			addCondition(field, new LiteralValue(value, DataType.BYTE));
		}else if(value instanceof Date){
			addCondition(field, new LiteralValue(value, DataType.DATE));
		}else if(value instanceof BigDecimal){
			addCondition(field, new LiteralValue(value, DataType.BIG_DECIMAL));
		}else if(value instanceof List<?>){
			List<LiteralValue> literalValueList=new ArrayList<LiteralValue>();
			for(Object obj : (List<?>)value){
				if(obj instanceof String){
					literalValueList.add(new LiteralValue((String) obj, DataType.STRING));
				}else if(obj instanceof Integer){
					literalValueList.add(new LiteralValue(obj, DataType.INT));
				}else if(obj instanceof Long){
					literalValueList.add(new LiteralValue(obj, DataType.LONG));
				}else if(obj instanceof Short){
					literalValueList.add(new LiteralValue(obj, DataType.SHORT));
				}else if(obj instanceof Float){
					literalValueList.add(new LiteralValue(obj, DataType.FLOAT));
				}else if(obj instanceof Double){
					literalValueList.add(new LiteralValue(obj, DataType.DOUBLE));
				}else if(obj instanceof Boolean){
					literalValueList.add(new LiteralValue(obj, DataType.BOOLEAN));
				}else if(obj instanceof Byte){
					literalValueList.add(new LiteralValue(obj, DataType.BYTE));
				}else if(obj instanceof Date){
					literalValueList.add(new LiteralValue(obj, DataType.DATE));
				}else if(obj instanceof BigDecimal){
					literalValueList.add(new LiteralValue(obj, DataType.BIG_DECIMAL));
				}
			}
			addCondition(field, literalValueList);
		}
	}
	
	public void addJoin(JoinClause join){
		ArrayList<JoinClause> joins=simpleQuery.getJoins();
		if(joins == null){
			joins=new ArrayList<JoinClause>();
		}
		simpleQuery.setJoins(joins);
		joins.add(join);
	}
	
	public JoinClause addJoin(Table table, ComplexCondition condition){
		JoinClause join=new JoinClause();
		join.setTable(table);
		join.setCondition(condition);
		addJoin(join);
		return join;
	}
	
	public JoinClause addJoin(String tableName, String fieldName1, String fieldName2, ComparisonOperator operator) throws ArgumentNullException{
		if(fieldName1 == null || fieldName1.isEmpty())
			throw new ArgumentNullException("the field name was null");
		FromClause fromCaluse = simpleQuery.getFromClause();
		if(fromCaluse == null)
			throw new ArgumentNullException("the from clause was null");
		Table table1=fromCaluse.getTable();
		if(table1 == null)
			throw new ArgumentNullException("the table was null");
		Field field1=table1.findField(fieldName1);
		if(field1 == null){
			field1=table1.addField(fieldName1, null);
		}
		
		Table table2=new Table(tableName, tableName+"_alias");
		Field field2= table2.addField(fieldName2, null);
		
		ComplexCondition condition=new ComplexCondition();
		condition.setOperator(LogicalOperator.AND);
		condition.addCondition(
				new SimpleCondition(operator, 
					new FieldOperand(table1, field1), 
					new FieldOperand(table2, field2)));
		
		return addJoin(table2, condition);
	}
	
	private ComplexCondition getCondition(){
		ComplexCondition condition=(ComplexCondition) simpleQuery.getCondition();
		if(condition == null){
			condition=new ComplexCondition();
			simpleQuery.setCondition(condition);
			condition.setOperator(LogicalOperator.AND);
		}
		return condition;
	}

	public Query getSimpleQuery() {
		return simpleQuery;
	}

	public void setSimpleQuery(Query simpleQuery) {
		this.simpleQuery = simpleQuery;
	}
	
	
}
