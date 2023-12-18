package com.arqui.integrador.util;

import java.util.ArrayList;
import java.util.List;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.model.Account;
import com.arqui.integrador.model.User;

public class UserMapper {
	
	public static UserDto entityToDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.cellphone(user.getCellphone())
				.email(user.getEmail())
				.firstname(user.getFirstname())
				.surname(user.getSurname())
				.accounts(getAccountsDtoFromAccount(user.getAccounts()))
				.build();
	}

	public static User dtoToEntity(UserDto userDto) {
		return User.builder()
				.id(userDto.getId())
				.name(userDto.getName())
				.cellphone(userDto.getCellphone())
				.email(userDto.getEmail())
				.firstname(userDto.getFirstname())
				.surname(userDto.getSurname())
				.build();
	}
	
	private static List<AccountDto> getAccountsDtoFromAccount(List<Account> accounts) {
		if(accounts == null || accounts.isEmpty())
			return new ArrayList<AccountDto>();
		return accounts.stream().map(e -> AccountMapper.entityToDto(e)).toList();
	}
}
