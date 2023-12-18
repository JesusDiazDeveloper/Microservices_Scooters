package com.arqui.integrador.exception;

import org.springframework.http.HttpStatus;

public class AccountHasUserException extends CustomException{

	private static final long serialVersionUID = 1L;

	public AccountHasUserException(String error, String description) {
		super(HttpStatus.BAD_REQUEST, error, description);
	}

}
