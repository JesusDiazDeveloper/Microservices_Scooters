package com.arqui.integrador.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotNull
	private List<String> roles;
}
