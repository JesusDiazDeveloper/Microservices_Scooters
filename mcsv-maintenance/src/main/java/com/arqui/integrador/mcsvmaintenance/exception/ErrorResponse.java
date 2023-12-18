package com.arqui.integrador.mcsvmaintenance.exception;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
	private int status;
	
	private String error;
	
	private String message;
	
	private Timestamp timestamp;	
}
