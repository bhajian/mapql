package com.galionet.queryBuilder;

import java.io.Serializable;

import com.citi.cept.cps.util.DataType;

public class LiteralValue implements Serializable {

	private static final long serialVersionUID = 4724039673671296100L;
	private Object value;
	private DataType type;

	public LiteralValue(){
		
	}
	public LiteralValue(Object value, DataType type){
		this.value=value;
		this.type=type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
}
