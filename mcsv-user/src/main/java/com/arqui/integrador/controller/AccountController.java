package com.arqui.integrador.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.service.IAccountService;

import jakarta.validation.Valid;

@RestController
public class AccountController implements IAccountController{
	
	private IAccountService accountService;
	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
	
	public AccountController(IAccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public ResponseEntity<List<AccountDto>> getAll() {
		List<AccountDto> response = this.accountService.getAll();
		
		LOG.info("Getting all accounts: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<AccountDto> getById(Long id) {
		AccountDto response = this.accountService.getById(id);
		
		LOG.info("Getting account by id: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<AccountDto> create(@Valid AccountDto accountDto) {
		AccountDto response = this.accountService.create(accountDto);
		
		LOG.info("Creating account: {}", response);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<AccountDto> update(Long id, @Valid AccountDto accountDto) {
		AccountDto response = this.accountService.update(id, accountDto);
		
		LOG.info("Updating account: {} with id: {}", accountDto, id);
		
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<AccountDto> addUser(@PathVariable(name = "account-id") Long id, @Valid @RequestBody UserDto userDto){
		AccountDto response = this.accountService.addUser(id, userDto);
		
		LOG.info("Adding user: {} into account: {}", userDto, response);
		
		return ResponseEntity.ok(response);
	}
	
	@Override
	public void authorizeAccount(Long id){
		AccountDto response = this.accountService.authorize(id);
		
		LOG.info("Authorizing account: {}", response);
		}
	
	@Override
	public void unauthorizeAccount(Long id){
		AccountDto response = this.accountService.unauthorize(id);
		
		LOG.info("Unauthorizing account: {}", response);		
	}

	@Override
	public void delete(Long id) {
		this.accountService.delete(id);
		
		LOG.info("Deleting account with id: {}", id);
	}
	
	@Override
	public void deleteUser(Long accountId, Long userId) {
		this.accountService.deleteUser(accountId, userId);
		
		LOG.info("Deleting userId: {} from accountId: {}", userId, accountId);
	}
}
