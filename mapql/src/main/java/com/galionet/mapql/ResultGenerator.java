package com.galionet.mapql;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.galionet.queryBuilder.ArgumentNullException;
import com.galionet.queryBuilder.ComparisonOperator;
import com.galionet.queryBuilder.ComplexCondition;
import com.galionet.queryBuilder.Condition;
import com.galionet.queryBuilder.FieldOperand;
import com.galionet.queryBuilder.FromClause;
import com.galionet.queryBuilder.LiteralOperand;
import com.galionet.queryBuilder.LogicalOperator;
import com.galionet.queryBuilder.Operand;
import com.galionet.queryBuilder.Projection;
import com.galionet.queryBuilder.Query;
import com.galionet.queryBuilder.SimpleCondition;
import com.galionet.queryBuilder.Table;



public class ResultGenerator {
	
	
	public Set<?> executeQuery(Query query, InMemDB db) throws ArgumentNullException, CacheException {
		if(query == null)
			throw new ArgumentNullException("query is null");
		FromClause fromClause=query.getFromClause();
		if(fromClause == null)
			throw new ArgumentNullException("fromClause is null");
		Table table=fromClause.getTable();
		if(table == null)
			throw new ArgumentNullException("table is null");
		Projection projection=query.getProjection();
		if(projection == null)
			throw new ArgumentNullException("projection is null");
		Condition condition=query.getCondition();
		DBTable dataTable=db.getDbTables().get(table.getName());
		
		Set<Row> keySet=processCondition(condition, dataTable);
		return keySet;
	}
	
	private Set<Row> processCondition(Condition condition, DBTable dataTable) throws CacheException{
		Set<Row> keySet=null;
		if(condition != null){
			if(condition instanceof SimpleCondition){
				keySet=processSimpleCondition((SimpleCondition) condition, dataTable);
			}
			if(condition instanceof ComplexCondition){
				keySet=processComplexCondition((ComplexCondition) condition, dataTable);
			}
		}else{
			keySet=dataTable.getAllRows();
		}
		return keySet;
	}
	
	private Set<Row> processSimpleCondition(SimpleCondition simpleCondition, DBTable dataTable) throws CacheException{
		Operand operand1= simpleCondition.getOperand1();
		Operand operand2= simpleCondition.getOperand2();
		ComparisonOperator operator= simpleCondition.getOperator();

		if(operand1 instanceof FieldOperand &&
				operand2 instanceof FieldOperand){
			FieldOperand fieldOperand1=(FieldOperand) operand1;
			FieldOperand fieldOperand2=(FieldOperand) operand2;
			return getResultSetComparingTwoColumnsValue(dataTable, fieldOperand1.getField().getName()
					,fieldOperand2.getField().getName(), operator);
		}
		if(operand1 instanceof FieldOperand &&
				operand2 instanceof LiteralOperand){
			FieldOperand fieldOperand1=(FieldOperand) operand1;
			LiteralOperand literalOperand2=(LiteralOperand) operand2;
			if(operator == ComparisonOperator.EQUALS){
				return getResultSetByColumnValue(dataTable, fieldOperand1.getField().getName(), literalOperand2.getLiteralValue().getValue(), operator);
			}

		}
		if(operand1 instanceof LiteralOperand &&
				operand2 instanceof LiteralOperand){
			LiteralOperand literalOperand1=(LiteralOperand) operand1;
			LiteralOperand literalOperand2=(LiteralOperand) operand2;
			if(literalOperand1.getLiteralValue().getType() == literalOperand2.getLiteralValue().getType()
					&& literalOperand1.getLiteralValue().getValue().equals(literalOperand2.getLiteralValue().getValue())){
				return dataTable.getAllRows();
			}
		}
		if(operand1 instanceof LiteralOperand &&
				operand2 instanceof FieldOperand){
			LiteralOperand literalOperand1=(LiteralOperand) operand1;
			FieldOperand fieldOperand2=(FieldOperand) operand2;
			if(operator == ComparisonOperator.EQUALS){
				return getResultSetByColumnValue(dataTable, fieldOperand2.getField().getName(), literalOperand1.getLiteralValue().getValue(), operator);
			}
		}
		return null;
	}
	
	private Set<Row> processComplexCondition(ComplexCondition complexCondition, DBTable dataTable) throws CacheException{
		Set<Row> result=new HashSet<Row>();
		LogicalOperator operator=complexCondition.getOperator();
		int i=0;
		for(Condition c:complexCondition.getConditions()){
			Set<Row> tmp=null;
			if(c instanceof SimpleCondition){
				tmp = processSimpleCondition((SimpleCondition) c, dataTable);
			}
			if(c instanceof ComplexCondition){
				tmp=processComplexCondition((ComplexCondition) c, dataTable);
			}
			if(i<1 && tmp != null){
				result.addAll(tmp);
			}else{
				if(tmp != null && result != null && operator == LogicalOperator.AND){
					result.retainAll(tmp);
				}
				if(tmp != null && result != null && operator == LogicalOperator.OR){
					result.addAll(tmp);
				}
			}
			i++;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<Object> intersect(HashSet<Object> set1, HashSet<Object> set2){
		HashSet<Object> tmp=(HashSet<Object>) set1.clone();
		tmp.retainAll(set2);
		return tmp;
	}

	public HashSet<Row> getResultSetByColumnValue(DBTable dbTable, String columnName,
			Object cellValue, ComparisonOperator operator) {
		HashSet<Row> rows = null;
		if (dbTable.getIndeces().keySet().contains(columnName)) {
			rows=getResultSetByColumnValueByIndex(dbTable, columnName, cellValue, operator);
		} else {
			rows=getResultSetByColumnValueByScan(dbTable, columnName, cellValue, operator);
		}
		return rows;
	}
	
	public HashSet<Row> getResultSetByColumnValueByIndex(DBTable dbTable, String columnName,
			Object cellValue, ComparisonOperator operator) {
		HashSet<Row> rows = new HashSet<Row>();
		Index indx = dbTable.getIndeces().get(columnName);
		DataType dt = dbTable.getSchema().get(columnName).getDataType();
		if (operator == ComparisonOperator.GREATER
				|| operator == ComparisonOperator.GREATER_EQUAL) {
			if (indx instanceof TreeIndex) {
				rows=(((TreeIndex) indx)
						.getPKsGreaterThanValue(DataTypeHelper.stringToPrimitiveType(cellValue.toString(), dt)));
			}
		}
		if(operator == ComparisonOperator.LESS
				|| operator == ComparisonOperator.LESS_EQUAL){
			if (indx instanceof TreeIndex) {
				rows=(((TreeIndex) indx)
						.getPKsLessThanValue(DataTypeHelper.stringToPrimitiveType(cellValue.toString(), dt)));
			}
		}
		if(operator == ComparisonOperator.EQUALS
				|| operator == ComparisonOperator.GREATER_EQUAL
				|| operator == ComparisonOperator.LESS_EQUAL) {
			HashSet<Row> res=indx.getPKsEqualToValue(DataTypeHelper.stringToPrimitiveType(cellValue.toString(), dt));
			if(res != null){
				rows.addAll(res);
			}
		}if(operator == ComparisonOperator.NOT_EQUALS){
			
		}if(operator == ComparisonOperator.IS){
			
		}if(operator == ComparisonOperator.IS_NOT){
			
		}if(operator == ComparisonOperator.IN){
			
		}
//		if(operator == ComparisonOperator.LIKE){
//			
//		}
		return rows;
	}

	public HashSet<Row> getResultSetByColumnValueByScan(DBTable dbTable, String columnName,
			Object cellValue, ComparisonOperator operator) {
		HashSet<Row> result = new HashSet<Row>();
		Collection<Row> rows= dbTable.getDataTable().values();
		for(Row r:rows){
			Object value=r.getValue(columnName);
			if(cellValue.equals(value)){
				result.add(r);
			}
		}
		return result;
	}

	public HashSet<Row> getResultSetComparingTwoColumnsValue(DBTable dbTable, String columnName1,
			String columnName2, ComparisonOperator operator) {
		HashSet<Row> result = new HashSet<Row>();
		Collection<Row> rows= dbTable.getDataTable().values();
		for(Row r:rows){
			Object value1=r.getValue(columnName1);
			Object value2=r.getValue(columnName2);
			if(value1.equals(value2)){
				result.add(r);
			}
		}
		return result;
	}
	
	public Set<Row> hashJoin(Set<Row> rowSet1, Set<Row> rowSet2,
			String column1, String column2, ComparisonOperator operator) {
		HashSet<Row> joinedResultSet=new HashSet<Row>();
		
		Set<Row> smallerSet=null;
		Set<Row> biggerSet=null;
		String smallerSetCol=null;
		String biggerSetCol=null;
		if(rowSet1.size() > rowSet2.size()){
			biggerSet=rowSet1;
			smallerSet=rowSet2;
			biggerSetCol=column1;
			smallerSetCol=column2;
		}else{
			biggerSet=rowSet2;
			smallerSet=rowSet1;
			biggerSetCol=column2;
			smallerSetCol=column1;
		}
		Map<Object, List<Row>> valueMapper=null;
		if(operator == ComparisonOperator.EQUALS || operator == ComparisonOperator.NOT_EQUALS){
			valueMapper=new HashMap<Object, List<Row>>();
		}
		if(operator == ComparisonOperator.GREATER || operator == ComparisonOperator.GREATER_EQUAL
				|| operator == ComparisonOperator.LESS || operator == ComparisonOperator.LESS_EQUAL){
			valueMapper=new TreeMap<Object, List<Row>>();
		}
		for(Row row:biggerSet){
			List<Row> tmpList=null;
			Object keyValue=row.getValue(biggerSetCol);
			if(keyValue != null){
				tmpList=valueMapper.get(keyValue);
			}
			if(tmpList == null){
				tmpList=new ArrayList<Row>();
			}
			tmpList.add(row);
			valueMapper.put(row.getValue(biggerSetCol), tmpList);
		}
		for(Row row1:smallerSet){
			List<Row> biggerFilteredList=null;
			Object smallerSetValue=row1.getValue(smallerSetCol);
			if(smallerSetValue != null){
				if(operator == ComparisonOperator.GREATER 
						|| operator == ComparisonOperator.GREATER_EQUAL ){
					TreeMap<Object, List<Row>> treeValueMapper=(TreeMap<Object, List<Row>>) valueMapper;
					biggerFilteredList=getGreaterThanValue(treeValueMapper, smallerSetValue);
				}
				if(operator == ComparisonOperator.LESS
						|| operator == ComparisonOperator.LESS_EQUAL){
					TreeMap<Object, List<Row>> treeValueMapper=(TreeMap<Object, List<Row>>) valueMapper;
					biggerFilteredList=getLessThanValue(treeValueMapper, smallerSetValue);
				}
				if(operator == ComparisonOperator.EQUALS 
						|| operator == ComparisonOperator.GREATER_EQUAL 
						|| operator == ComparisonOperator.LESS_EQUAL){
					if(biggerFilteredList != null){
						biggerFilteredList.addAll(valueMapper.get(smallerSetValue));
					}else{
						biggerFilteredList=valueMapper.get(smallerSetValue);
					}
				}
				if(operator == ComparisonOperator.NOT_EQUALS){
					List<Row> tmpList1=valueMapper.get(smallerSetValue);
					List<Row> tmpList2=new ArrayList<Row>();
					tmpList2.addAll(biggerSet);
					tmpList2.removeAll(tmpList1);
					if(biggerFilteredList != null){
						biggerFilteredList.addAll(tmpList2);
					}else{
						biggerFilteredList=tmpList2;
					}
				}
				if(biggerFilteredList != null){
					for(Row row2:biggerFilteredList){
						Row joinedRow=joinTwoRows(row1, row2);
						joinedResultSet.add(joinedRow);
					}
				}
			}
			
		}
		return joinedResultSet;
	}
	
	public List<Row> getGreaterThanValue(TreeMap<Object, List<Row>> map, Object columnValue){
		List<Row> results=new ArrayList<Row>();
		SortedMap<Object, List<Row>> tmp=map.tailMap(columnValue);
		for(List<Row> hs: tmp.values()){
			results.addAll(hs);
		}
		return results;
	}
	
	public List<Row> getLessThanValue(TreeMap<Object, List<Row>> map, Object columnValue){
		List<Row> results=new ArrayList<Row>();
		SortedMap<Object, List<Row>> tmp=map.headMap(columnValue);
		for(List<Row> hs: tmp.values()){
			results.addAll(hs);
		}
		return results;
	}
	
	private Row joinTwoRows(Row row1, Row row2){
		Row joinedRow=new Row();
		for(String colName:row1.getDataRowMap().keySet()){
			joinedRow.putValue(colName, row1.getValue(colName));
		}
		for(String colName:row2.getDataRowMap().keySet()){
			Object existedVal=joinedRow.getValue(colName);
			joinedRow.putValue((existedVal == null ? colName : colName+"_1"), row2.getValue(colName));
		}
		return joinedRow;
	}
	
	private boolean evaluateTwoRows(Row row1, Row row2, ComparisonOperator operator){
		return false;
		
	}

//	public Set<Row> getRecordsForKeySet(DBTable dbTable, Set<Object> keys, List<String> selectedColumns) {
//		Set<Row> result = new HashSet<Row>();
//		for (Object key : keys) {
//			Row selectedRow=dbTable.getDataTable().get(key);
//			if(selectedColumns == null){
//				result.add(selectedRow);
//			}else{
//				Row dataRow=new Row();
//				for(String columnName:selectedColumns){
//					dataRow.putValue(columnName, selectedRow.getValue(columnName));
//				}
//				result.add(dataRow);
//			}
//		}
//		return result;
//	}
	
	public int deleteRows(String tableName, Condition condition, InMemDB db) throws CacheException{
		DBTable dbTable=db.getDbTables().get(tableName);
		Set<Row> keySet=processCondition(condition, dbTable);
//		dbTable.deleteRows(keySet);
		//////////////////////////////////////////
		return 0;
	}
	
	private void updateIndex(Row oldRow, Row newRow){
		
	}
	
	public int updateRows(String tableName, String columnName, Object value,
			Condition condition, InMemDB db) throws CacheException{
		DBTable dbTable=db.getDbTables().get(tableName);
		Set<Row> keySet=processCondition(condition, dbTable);
//		dbTable.updateRows(keySet, columnName, value);
		//////////////////////////////////////////
		return 0;
	}
}
