package com.galionet.mapql;

/**
 * @author bh48059
 *
 */
public class CacheException extends Exception
 {

	private static final long serialVersionUID = -5760927360840548213L;
	String message;
	
	public CacheException(Exception ex){
		this.message=ex.getMessage();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
