package com.arqui.integrador.dto;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.Email;
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
public class UserDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private Long cellphone;
	
	@Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;
	
	@NotBlank
	private String firstname;
	
	@NotBlank
	private String surname;
	
	private List<AccountDto> accounts;
}
