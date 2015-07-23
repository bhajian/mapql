package com.galionet.queryBuilder;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("fieldOperand") 
public class FieldOperand extends Operand {

	private static final long serialVersionUID = 2885497928906542899L;
	private Field field;
	private Table table;
	
	public FieldOperand(){
		
	}
	public FieldOperand(Table table, Field field){
		this.field=field;
		this.setTable(table);
	}
	public FieldOperand(Table table,String name, String alias){
		field=new Field(name, alias);
		this.setTable(table);
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.fieldOperandToSQL(this);
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	
}
