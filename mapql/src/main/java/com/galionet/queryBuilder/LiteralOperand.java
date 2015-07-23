package com.galionet.queryBuilder;

import org.codehaus.jackson.annotate.JsonTypeName;
import com.citi.cept.cps.util.DataType;

@JsonTypeName("literalOperand") 
public class LiteralOperand extends Operand {

	private static final long serialVersionUID = 1988295761553365336L;
	private LiteralValue literalValue;

	public LiteralOperand(){
		
	}
	public LiteralOperand(LiteralValue literalValue){
		this.literalValue=literalValue;
	}
	public LiteralOperand(String value, DataType type){
		literalValue=new LiteralValue(value, type);
	}
	public LiteralValue getLiteralValue() {
		return literalValue;
	}
	public void setLiteralValue(LiteralValue literalValue) {
		this.literalValue = literalValue;
	}
	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.literalOperandToSQL(this);
	}
	
}
