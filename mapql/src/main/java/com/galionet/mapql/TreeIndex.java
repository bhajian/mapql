package com.galionet.mapql;


import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TreeIndex extends Index{

	public TreeIndex(){
		super();
		indexTable=new TreeMap<Object, HashSet<Row>>();
	}
	public TreeIndex(String indexName, String columnName, Map<String, MetaData> schema){
		super(indexName, columnName, schema);
		indexTable=new TreeMap<Object, HashSet<Row>>();
	}
	public HashSet<Row> getPKsLessThanValue(Object columnValue){
		HashSet<Row> results=new HashSet<Row>();
		SortedMap<Object, HashSet<Row>> tmp=((TreeMap<Object, HashSet<Row>>)indexTable).headMap(columnValue);
		for(HashSet<Row> hs: tmp.values()){
			results.addAll(hs);
		}
		return results;
	}
	public HashSet<Row> getPKsGreaterThanValue(Object columnValue){
		HashSet<Row> results=new HashSet<Row>();
		SortedMap<Object, HashSet<Row>> tmp=((TreeMap<Object, HashSet<Row>>)indexTable).tailMap(columnValue);
		for(HashSet<Row> hs: tmp.values()){
			results.addAll(hs);
		}
		return results;
	}
}