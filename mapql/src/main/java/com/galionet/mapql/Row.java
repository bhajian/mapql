package com.galionet.mapql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class Row implements Serializable {

	private static final long serialVersionUID = 4199016820439383462L;
	private HashMap<String, Object> dataRowMap;

	public Row(){
		if(dataRowMap == null){
			dataRowMap=new HashMap<String, Object>();
		}
	}
	public Object getValue(String ColumnName){
		return dataRowMap.get(ColumnName);
	}
	public HashMap<String, Object> getDataRowMap() {
		return dataRowMap;
	}
	public Object getPK(List<String> pkColList){
		if(pkColList == null)
			return null;
		if(pkColList.size()==1){
			return dataRowMap.get(pkColList.get(0));
		}else if(pkColList.size()>1){
			StringBuilder sb=new StringBuilder();
			int i=0;
			for(String col: pkColList){
				sb.append(dataRowMap.get(col));
				i++;
				if(i<pkColList.size()){
					sb.append("_");
				}
			}
			return sb.toString();
		}
		return null;
	}
	public void putValue(String columnName, Object value){
		dataRowMap.put(columnName, value);
	}
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		int i=0;
		for(Entry<String, Object> o: dataRowMap.entrySet()){
			i++;
			sb.append(o.toString());
			if(i<dataRowMap.entrySet().size()){
				sb.append(" , ");
			}
		}
		return sb.toString();
	}
	@Override
	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if (o == null){
			return false;
		}
		if(!(o instanceof Row)){
			return false;
		}
		if(o.hashCode() != this.hashCode()){
			return false;
		}
		Row other=(Row) o;
		if(other.dataRowMap.size() != this.dataRowMap.size()){
			return false;
		}
		for(Entry<String, Object> thisEntry: dataRowMap.entrySet()){
			Object otherValue=other.dataRowMap.get(thisEntry.getKey());
			Object thisValue=thisEntry.getValue();
			if(!otherValue.equals(thisValue)){
				return false;
			}
		}
		return true;
	}
	@Override
	public int hashCode(){
		StringBuilder sb=new StringBuilder();
		for(String s:dataRowMap.keySet()){
			sb.append(dataRowMap.get(s));
		}
		return sb.toString().hashCode();
	}
}
