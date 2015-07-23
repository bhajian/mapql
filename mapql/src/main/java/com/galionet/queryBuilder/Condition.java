package com.galionet.queryBuilder;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "type")  
	@JsonSubTypes({  
	    @Type(value = ComplexCondition.class, name = "complexCondition"),  
	    @Type(value = SimpleCondition.class, name = "simpleCondition") }) 
public abstract class Condition extends Clause {

	private static final long serialVersionUID = 5312701315404654788L;
	public String name;

}
