package com.arqui.integrador.util;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.model.Account;

public class AccountMapper {
	
	public static AccountDto entityToDto(Account account) {
		return AccountDto.builder()
				.id(account.getId())
				.dateOfSign(account.getDateOfSign())
				.amount(account.getAmount())
				.mpAccount(account.getMpAccount())
				.isAvailable(account.isAvailable())
				.build();
	}

	public static Account dtoToEntity(AccountDto accountDto) {
		return Account.builder()
				.id(accountDto.getId())
				.dateOfSign(accountDto.getDateOfSign())
				.amount(accountDto.getAmount())
				.mpAccount(accountDto.getMpAccount())
				.isAvailable(accountDto.isAvailable())
				.build();
	}
}
