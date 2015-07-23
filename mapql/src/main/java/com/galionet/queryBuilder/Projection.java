package com.galionet.queryBuilder;

import java.util.ArrayList;

public class Projection extends Clause {

	private static final long serialVersionUID = -7005887200210400587L;
	private ArrayList<Field> fields;
	
	public Projection(){
		fields=new ArrayList<Field>();
	}
	
	public Projection(ArrayList<Field> fields){
		this.fields=fields;
	}

	public void addField(Field field){
		this.fields.add(field);
	}
	
	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.projectionToSQL(this);
	}
	
}
