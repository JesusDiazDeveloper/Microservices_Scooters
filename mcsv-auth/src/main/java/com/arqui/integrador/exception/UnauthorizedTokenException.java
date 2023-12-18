package com.arqui.integrador.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedTokenException extends CustomException{

	private static final long serialVersionUID = 1L;

	public UnauthorizedTokenException(String error, String description) {
		super(HttpStatus.UNAUTHORIZED, error, description);
	}

}
