package com.arqui.integrador.exception;

import org.springframework.http.HttpStatus;

public class CustomBadCredentialsException extends CustomException{

	private static final long serialVersionUID = 1L;

	public CustomBadCredentialsException(String error, String description) {
		super(HttpStatus.UNAUTHORIZED, error, description);
	}

}
