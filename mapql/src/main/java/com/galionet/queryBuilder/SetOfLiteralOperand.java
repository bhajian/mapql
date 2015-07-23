package com.galionet.queryBuilder;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("setOfLiteralOperand") 
public class SetOfLiteralOperand extends Operand {

	private static final long serialVersionUID = 8159924983694591209L;
	private List<LiteralValue> literalValueSet;
	
	public SetOfLiteralOperand(){
		literalValueSet=new ArrayList<LiteralValue>();
	}

	public SetOfLiteralOperand(List<LiteralValue> literalValueSet){
		this.literalValueSet=literalValueSet;
	}
	
	public List<LiteralValue> getLiteralValueSet() {
		return literalValueSet;
	}

	public void setLiteralValueSet(List<LiteralValue> literalValueSet) {
		this.literalValueSet = literalValueSet;
	}

	@Override
	public String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException {
		return sqlGenerator.setOfLiteralOperandToSQL(this);
	}
}
