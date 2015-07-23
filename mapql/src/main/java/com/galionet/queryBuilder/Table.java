package com.galionet.queryBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Table implements Serializable {
	
	private static final long serialVersionUID = 98842442784671681L;
	private String name;
	private String alias;
	private Map<String, Field> fields;
	
	public Table(){
		
	}
	public Table(String name, String alias){
		this.name=name;
		this.alias=alias;
		this.fields=new HashMap<String, Field>();
	}
	public Field addField(String name, String alias){
		Field field=new Field(name, alias);
		field.setTable(this);
		this.fields.put(name, field);
		return field;
	}
	public Field findField(String name){
		return fields.get(name);
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
	public String getTableNameOrAlias(){
		if(alias != null){
			return alias;
		}else{
			return name;
		}
	}
	@JsonIgnore
	public String getTableNameWithAlias(){
		StringBuilder sb=new StringBuilder();
		sb.append(getName());
		if(getAlias() != null){
			sb.append(" "+getAlias());
		}
		return sb.toString();
	}
	public Map<String, Field> getFields() {
		return fields;
	}
	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}

}
