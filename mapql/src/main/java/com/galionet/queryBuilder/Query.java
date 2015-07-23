package com.galionet.queryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Query implements Serializable {

	private static final long serialVersionUID = -8207791106329776962L;
	private Projection projection;
	private FromClause fromClause;
	private Condition condition;
	private ArrayList<JoinClause> joins;
	private Query subQuery;
	private boolean distinct;
	
	
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	public FromClause getFromClause() {
		return fromClause;
	}
	public void setFromClause(FromClause fromClause) {
		this.fromClause = fromClause;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public ArrayList<JoinClause> getJoins() {
		return joins;
	}
	public void setJoins(ArrayList<JoinClause> joins) {
		this.joins = joins;
	}
	public Query getSubQuery() {
		return subQuery;
	}
	public void setSubQuery(Query subQuery) {
		this.subQuery = subQuery;
	}
	public boolean isDistinct() {
		return distinct;
	}
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	@JsonIgnore
	public List<Table> getAllTables(){
		ArrayList<Table> tables=new ArrayList<Table>();
		tables.add(this.fromClause.getTable());
		if(joins != null){
			for(JoinClause jc:joins){
				tables.add(jc.getTable());
			}
		}
		if(this.subQuery != null){
			tables.addAll(this.subQuery.getAllTables());
		}
		return tables;
	}
	@JsonIgnore
	public Table getTable(Field field){
		for(Table t:getAllTables()){
			if(t.getFields().containsValue(field)){
				return t;
			}
		}
		return null;
	}
	
}
