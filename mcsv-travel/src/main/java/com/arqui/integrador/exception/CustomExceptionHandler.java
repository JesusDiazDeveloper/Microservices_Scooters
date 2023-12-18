package com.arqui.integrador.exception;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ce) {

		ErrorResponse er = new ErrorResponse(ce.getStatusCode().value(), ce.getMessage(), ce.getDescription(),
				Timestamp.valueOf(LocalDateTime.now()));
		
		StringBuilder error = new StringBuilder("Error summary:\n");
		
		error.append("Status code: " + ce.getStatusCode().value() + "\n");
		error.append("Error: " + ce.getMessage() + "\n");
		error.append("Description: " + ce.getDescription() + "\n");
		error.append("Date: " + LocalDate.now().toString());
		
		LOG.error(error.toString());
		
		return new ResponseEntity<>(er, ce.getStatusCode());
	}
}
