package com.galionet.mapql;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class HashIndex extends Index{

	public HashIndex(){
		super();
		indexTable=new HashMap<Object, HashSet<Row>>();
	}
	public HashIndex(String indexName, String columnName, Map<String, MetaData> schema){
		super(indexName, columnName, schema);
		indexTable=new HashMap<Object, HashSet<Row>>();
	}
	
}
