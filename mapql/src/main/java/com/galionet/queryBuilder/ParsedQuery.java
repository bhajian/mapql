package com.galionet.queryBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public class ParsedQuery implements Serializable {

	private static final long serialVersionUID = 2190280689321938467L;
	private ArrayList<LiteralValue> parameters=new ArrayList<LiteralValue>();
	private String SqlQuery;
	
	public ArrayList<LiteralValue> getParameters() {
		return parameters;
	}
	public void setParameters(ArrayList<LiteralValue> parameters) {
		this.parameters = parameters;
	}
	public String getSqlQuery() {
		return SqlQuery;
	}
	public void setSqlQuery(String sqlQuery) {
		SqlQuery = sqlQuery;
	} 
}
