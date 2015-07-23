package com.galionet.queryBuilder;

public class ArgumentNullException extends Exception {

	private static final long serialVersionUID = 6613448323706674410L;
	private String errorMessage="";
	
	public ArgumentNullException(){
		
	}
	
	public ArgumentNullException(String message){
		this.errorMessage=message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
