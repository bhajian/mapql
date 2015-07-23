package com.galionet.queryBuilder;

public enum ComparisonOperator {
	
	IS("IS"),
	IS_NOT("IS NOT"),
	LIKE("LIKE"),
	NOT_LIKE("NOT_LIKE"),
	EQUALS("="),
	NOT_EQUALS("!="),
	IN("IN"),
	NOT_IN("NOT IN"),
	GREATER(">"),
	LESS("<"),
	GREATER_EQUAL(">="),
	LESS_EQUAL("<=");
	
	private String stringValue;
	
	ComparisonOperator(String value){
		this.stringValue=value;
	}
	
	public String getStringValue(){
		return stringValue;
	}
}
