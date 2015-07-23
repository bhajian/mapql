package com.galionet.queryBuilder;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Field implements Serializable {
	
	private static final long serialVersionUID = -573618975815592897L;
	private String name;
	private String alias;
	private Table table;
	
	
	public Field(){
		
	}
	public Field(String name, String alias){
		this.name=name;
		this.alias=alias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	@JsonIgnore
	public String fieldNameWithAlias(){
		StringBuilder sb=new StringBuilder();
		sb.append(getName());
		if(getAlias() != null){
			sb.append(" AS "+getAlias());
		}
		return sb.toString();
	}
	@JsonIgnore
	public String getFullPathName(Table table){
		StringBuilder sb=new StringBuilder();
		if(table != null){
			sb.append(table.getTableNameOrAlias());
			sb.append(".");
		}
		sb.append(getName());
		return sb.toString();
	}
	@JsonIgnore
	public String getFullPathName(){
		return getFullPathName(this.table);
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}

}
