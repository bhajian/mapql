package com.galionet.queryBuilder;

import java.io.Serializable;

public abstract class Clause implements Serializable {

	private static final long serialVersionUID = 5795639651334374986L;
	public abstract String toSQL(SQLGenerator sqlGenerator) throws ArgumentNullException;
	
}
