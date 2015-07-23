package com.galionet.queryBuilder;

public class FromClause extends Clause{

	private static final long serialVersionUID = -9204838869200918794L;
	private Table table;
	
	public FromClause(){
		
	}
	public FromClause(String tableName, String alias){
		this.table=new Table(tableName, alias);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.fromClauseToSQL(this);
	}
}
