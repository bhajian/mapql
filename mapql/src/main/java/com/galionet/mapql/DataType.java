package com.galionet.mapql;

public enum DataType {
	STRING,
	INT,
	LONG,
	BYTE,
	SHORT,
	FLOAT,
	DOUBLE,
	BOOLEAN,
	BIG_DECIMAL,
	TIME_STAMP,
	DATE,
	NULL;
	
	public static void main(String args[]){
		
		
		DataType dayVal = DataType.valueOf("STRING");
		System.out.println(dayVal.name());	
	}
	
}
