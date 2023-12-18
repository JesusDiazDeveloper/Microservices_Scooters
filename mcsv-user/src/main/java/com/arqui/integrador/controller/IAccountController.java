package com.arqui.integrador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;

import jakarta.validation.Valid;

@RequestMapping("/accounts")
public interface IAccountController {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<List<AccountDto>> getAll();
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> getById(@PathVariable(name = "id")Long id);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto);
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> update(@PathVariable(name = "id") Long id, @Valid @RequestBody AccountDto accountDto);
	
	@PutMapping(value = "/{account-id}/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> addUser(@PathVariable(name = "account-id") Long id, @Valid @RequestBody UserDto userDto);
	
	@PutMapping(value = "/{account-id}/authorize")
	@ResponseStatus(HttpStatus.OK)
	void authorizeAccount(@PathVariable(name = "account-id") Long id);
	
	@PutMapping(value = "/{account-id}/unauthorize")
	@ResponseStatus(HttpStatus.OK)
	void unauthorizeAccount(@PathVariable(name = "account-id") Long id);
	
	@DeleteMapping(value = "/account/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/{account-id}/user/{user-id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteUser(@PathVariable(name = "account-id") Long accountId, @PathVariable(name = "user-id")  Long userId);
}
