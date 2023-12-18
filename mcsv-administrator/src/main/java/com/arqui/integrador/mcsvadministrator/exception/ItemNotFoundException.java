package com.arqui.integrador.mcsvadministrator.exception;


import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends CustomException{
	
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String error, String description) {
		super(HttpStatus.NOT_FOUND, error, description);
	}
}

