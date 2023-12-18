package com.arqui.integrador.mcsvadministrator.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
	
	protected HttpStatus statusCode;
	protected String description;
	
	public CustomException(HttpStatus statusCode, String error, String description) {
		super(error);
		this.statusCode = statusCode;
		this.description = description;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public String getDescription() {
		return description;
	}
}
