package com.arqui.integrador.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	
	private Long id;
	
	@NotNull
	private LocalDate dateOfSign;
	
	private double amount;
	
	@NotNull
	private int mpAccount;
	
	@NotNull
	private boolean isAvailable;
	
	private List<UserDto> users;

	@Override
	public String toString() {
		return "AccountDto [id=" + id + ", dateOfSign=" + dateOfSign + ", amount=" + amount + ", mpAccount=" + mpAccount
				+ ", isAvailable=" + isAvailable + "]";
	}
}