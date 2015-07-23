package com.galionet.mapql;


import java.util.HashSet;
import java.util.Map;

public abstract class Index {
	private String indexName;
	private String columnName;
	private Map<String, MetaData> schema;
	protected Map<Object, HashSet<Row>> indexTable;
	
	public Index(){
		
	}
	public Index(String indexName, String columnName, Map<String, MetaData> schema){
		this.indexName=indexName;
		this.columnName=columnName;
		this.schema=schema;
	}
	public HashSet<Row> getPKsEqualToValue(Object columnValue){
		return indexTable.get(columnValue);
	}
	public Map<Object, HashSet<Row>> getIndexTable() {
		return indexTable;
	}
	public void addEntry(Row row){
		DataType dt = schema.get(columnName).getDataType();
		Object value=null;
		if(row != null && DataTypeHelper.isStandardType(row.getValue(columnName))){
			value=DataTypeHelper.stringToPrimitiveType(row.getValue(columnName).toString(), dt);
		}
		HashSet<Row> list=indexTable.get(value);
		if(list == null){
			list=new HashSet<Row>();
			indexTable.put(value, list);
		}
		list.add(row);
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public Map<String, MetaData> getSchema() {
		return schema;
	}
	public void setSchema(Map<String, MetaData> schema) {
		this.schema = schema;
	}
}
