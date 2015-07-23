package com.galionet.queryBuilder;

public enum ArithmeticOperator {

	MINUS("-"),
	PLUS("+"),
	MULTIPLY("*"),
	DEVIDED("/");
	
	String stringValue;
	
	ArithmeticOperator(String value){
		this.stringValue=value;
	}
	
	public String getStringValue(){
		return stringValue;
	}
}
