package com.arqui.integrador.exception;

import org.springframework.http.HttpStatus;

public class UserHasAccountException extends CustomException{

	private static final long serialVersionUID = 1L;

	public UserHasAccountException(String error, String description) {
		super(HttpStatus.BAD_REQUEST, error, description);
	}
}
