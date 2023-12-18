package com.arqui.integrador.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException{

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String error, String description) {
		super(HttpStatus.NOT_FOUND, error, description);
	}
}
