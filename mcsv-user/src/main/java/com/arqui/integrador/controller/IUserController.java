package com.arqui.integrador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.arqui.integrador.dto.ScooterDto;
import com.arqui.integrador.dto.UserDto;

import jakarta.validation.Valid;

@RequestMapping("/users")
@Validated
public interface IUserController {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<List<UserDto>> getAll();
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<UserDto> getById(@PathVariable(name = "id") Long id);
	
	@GetMapping(value = "nearest-scooters/lat/{lat}/long/{long}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<List<ScooterDto>> getNearestScooters(@PathVariable(name = "lat") double latitude, @PathVariable(name = "long") double longitude);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<UserDto> create(@Valid @RequestBody UserDto user);
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<UserDto> update(@PathVariable(name = "id") Long id, @Valid @RequestBody UserDto user);
	
	@PutMapping(value = "/{user-id}/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<UserDto> addAccount(@PathVariable(name = "user-id") Long userId, @Valid @RequestBody AccountDto account);
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/{user-id}/account/{account-id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteAccount(@PathVariable(name = "user-id") Long userId, @PathVariable(name = "account-id") Long accountId);
}
