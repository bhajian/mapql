package com.galionet.mapql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DBTable {

	private String tableName;
	private List<String> primaryKeyColumnList;
	private HashMap<Object, Row> dataTable;
	private HashMap<String, Index> indeces;
	private HashMap<String, MetaData> schema;

	public HashMap<String, Index> getIndeces() {
		return indeces;
	}

	private void init() {
		if (primaryKeyColumnList == null) {
			primaryKeyColumnList = new ArrayList<String>();
		}
		if (dataTable == null) {
			dataTable = new HashMap<Object, Row>();
		}
		if (indeces == null) {
			indeces = new HashMap<String, Index>();
		}
		if (schema == null) {
			schema = new HashMap<String, MetaData>();
		}
	}

	public DBTable() {
		init();
	}

	public DBTable(String tableName) {
		init();
		this.tableName = tableName;
	}
	public Index createIndex(String columnName, IndexType indxType) {
		Index index = null;
		if(indxType == IndexType.HASH_TABLE){
			index = new HashIndex(columnName + "_indx", columnName, getSchema());
		}
		if(indxType == IndexType.TREE){
			index = new TreeIndex(columnName + "_indx", columnName, getSchema());
		}
		Set<Entry<Object, Row>> records = dataTable.entrySet();
		for (Entry<Object, Row> record : records) {
			index.addEntry(record.getValue());
		}
		indeces.put(columnName, index);
		return index;
	}
	public Set<Row> getAllRows(){
		HashSet<Row> resultSet=new HashSet<Row>();
		for(Entry<Object, Row> e:dataTable.entrySet()){
			resultSet.add(e.getValue());
		}
		return resultSet;
	}
	public Object addRow(Row row) {
		Object pk = row.getPK(primaryKeyColumnList);
		dataTable.put(pk, row);
		for (String columnName : indeces.keySet()) {
			Index index = indeces.get(columnName);
			index.addEntry(row);
		}
		return pk;
	}
	public int deleteRows(Set<Object> keySet){
		for(Object key: keySet){
			dataTable.remove(key);
		}
		return 0;
	}
	public int updateRows(Set<Object> keySet, String columnName, Object value){
		for(Object key: keySet){
			Row row=dataTable.get(key);
			row.putValue(columnName, value);
		}
		return 0;
	}
	public void addColumnNameAsPK(String columnName) {
		primaryKeyColumnList.add(columnName);
	}
	public Row getRowById(String Id) {
		return dataTable.get(Id);
	}
	public HashMap<Object, Row> getDataTable() {
		return dataTable;
	}
	public List<String> getPrimaryKeyColumnList() {
		return primaryKeyColumnList;
	}
	public void setPrimaryKeyColumnList(List<String> primaryKeyColumnName) {
		this.primaryKeyColumnList = primaryKeyColumnName;
	}
	public HashMap<String, MetaData> getSchema() {
		return schema;
	}
	public void setSchema(HashMap<String, MetaData> schema) {
		this.schema = schema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void addColumn(String columnName, boolean isUnique, boolean allowNull, DataType dataType, IndexType indexType){
		schema.put(columnName, new MetaData(columnName, isUnique, allowNull, dataType, indexType));
		if(indexType != IndexType.NONE){
			createIndex(columnName, indexType);
		}
	}
	public int addAll(List<Map<String,Object>> bulkData){
		for(Map<String,Object> record:bulkData){
			Row row=new Row();
			for(String col:record.keySet()){
				DataType dt = getSchema().get(col).getDataType();
				row.getDataRowMap().put(col, (record.get(col) != null ? DataTypeHelper.stringToPrimitiveType( record.get(col).toString() , dt): "") );
			}
			addRow(row);
		}
		
		return 0;
	}
	public void retract(){
		dataTable.clear();
		primaryKeyColumnList.clear();
		indeces.clear();
		schema.clear();
	}
}
