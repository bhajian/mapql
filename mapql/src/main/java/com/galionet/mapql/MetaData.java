package com.galionet.mapql;

public class MetaData {
	private String columnName;
	private boolean isUnique;
	private boolean allowNull;
	private DataType dataType;
	private IndexType indexType;
	
	public MetaData(){
		
	}
	public MetaData(String columnName, boolean isUnique, boolean allowNull, DataType dataType, IndexType indexType){
		this.columnName=columnName;
		this.isUnique=isUnique;
		this.allowNull=allowNull;
		this.dataType=dataType;
		this.indexType=indexType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public boolean isAllowNull() {
		return allowNull;
	}
	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public IndexType getIndexType() {
		return indexType;
	}
	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}
}
