package com.galionet.queryBuilder;

public enum LogicalOperator {
	AND("AND"),
	OR("OR"),
	NOT("NOT");
	
	private String stringValue;
	
	LogicalOperator(String value){
		this.stringValue=value;
	}
	
	public String getStringValue(){
		return stringValue;
	}
}
