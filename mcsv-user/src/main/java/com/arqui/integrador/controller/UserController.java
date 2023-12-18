package com.arqui.integrador.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.ScooterDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.service.IUserService;

@RestController
public class UserController implements IUserController{
	
	private IUserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@Override
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> response = this.userService.getAll();
		
		LOG.info("Getting all users: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<UserDto> getById(Long id) {
		UserDto response = this.userService.getById(id);
		
		LOG.info("Getting user by id: {}", response);
		
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<List<ScooterDto>> getNearestScooters(double latitude, double longitude){
		List<ScooterDto> response = this.userService.getNearScooters(latitude, longitude);
		
		LOG.info("Getting near scooters: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<UserDto> create(UserDto user) {
		UserDto response = this.userService.create(user);
		
		LOG.info("Creating user: {}", response);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserDto> update(Long id, UserDto user) {
		UserDto response = this.userService.update(id, user);
		
		LOG.info("Updating user: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<UserDto> addAccount(Long userId, AccountDto account) {
		UserDto response = this.userService.addAccount(userId, account);
		
		LOG.info("Adding account to user: ", response);
		
		return ResponseEntity.ok(response);
	}
	
	@Override
	public void delete(Long id) {
		this.userService.delete(id);
		
		LOG.info("Deleting user with id: {}", id);
	}

	@Override
	public void deleteAccount(Long userId,Long accountId) {
		this.userService.deleteAccount(userId, accountId);
		
		LOG.info("Deleting account id: {}, from user id: {}", accountId, userId);
	}

}
