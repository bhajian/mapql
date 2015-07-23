package com.galionet.mapql;

import java.util.HashMap;

public class InMemDB {
	
	private HashMap<String, DBTable> dbTables;

	public InMemDB(){
		if(dbTables == null){
			dbTables=new HashMap<String, DBTable>();
		}
	}
	private void addTable(DBTable table){
		dbTables.put(table.getTableName(), table);
	}
	public DBTable createTable(String tableName){
		DBTable table=new DBTable(tableName);
		addTable(table);
		return table;
	}
	public HashMap<String, DBTable> getDbTables() {
		return dbTables;
	}
	public int removeTable(String tableName){
		DBTable tbl=dbTables.remove(tableName);
		if(tbl != null){
			tbl.retract();
		}
		return (tbl == null ? 0 : 1);
	}
}
