package com.galionet.queryBuilder;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "type")  
	@JsonSubTypes({  
	    @Type(value = FieldOperand.class, name = "fieldOperand"),  
	    @Type(value = LiteralOperand.class, name = "literalOperand"),
	    @Type(value = SetOfLiteralOperand.class, name = "setOfLiteralOperand")}) 
public abstract class Operand extends Clause {

	private static final long serialVersionUID = -6345066775144258894L;
	
}
