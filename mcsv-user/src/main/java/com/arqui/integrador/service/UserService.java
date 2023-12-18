package com.arqui.integrador.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.ScooterDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.exception.UserHasAccountException;
import com.arqui.integrador.model.Account;
import com.arqui.integrador.model.User;
import com.arqui.integrador.repository.IAccountRepository;
import com.arqui.integrador.repository.IUserRepository;
import com.arqui.integrador.util.AccountMapper;
import com.arqui.integrador.util.UserMapper;

@Service
public class UserService implements IUserService{
	
	private IUserRepository userRepository;
	private IAccountRepository accountRepository;
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	private RestTemplate restTemplate;
	
	public UserService(IUserRepository userRepository, IAccountRepository accountRepository, RestTemplate restTemplate) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<UserDto> getAll() {
		List<UserDto> response = this.userRepository.findAll().stream().map(e -> UserMapper.entityToDto(e)).toList();
		
		LOG.info("Users: {}", response);
		
		return response;
	}

	@Override
	public UserDto getById(Long id) {
		User response = this.findById(id);
		
		LOG.info("User: {}", response);
		
		return UserMapper.entityToDto(response);
	}
	
	@Override
	public List<ScooterDto> getNearScooters(double latitude, double longitude){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<List<Void>> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<List<ScooterDto>> response = restTemplate.exchange(
				"lb://mcsv-scooter:8004/scooters/nearest/lat/" + latitude + "/long/" + longitude,
				HttpMethod.GET,
				requestEntity, 
				new ParameterizedTypeReference<List<ScooterDto>>() {}
		);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public UserDto create(UserDto userDto) {
		User response = this.userRepository.save(UserMapper.dtoToEntity(userDto));
		
		LOG.info("User: {}", response);
		
		return UserMapper.entityToDto(response);
	}

	@Override
	public UserDto update(Long id, UserDto userDto) {
		User user = this.findById(id);
		
		userDto.setId(user.getId());
		
		this.userRepository.save(UserMapper.dtoToEntity(userDto));
		
		LOG.info("User edited: {}", userDto);
		
		return userDto;
	}
	
	@Override
	public UserDto addAccount(Long userId, AccountDto accountDto) {
		User user = this.findById(userId);
		
		Optional<Account> accountOpt = this.accountRepository.findByMpAccount(accountDto.getMpAccount());
		
		Account account;
		
		if(accountOpt.isPresent()) {
			account = accountOpt.get();
		} else {
			account = AccountMapper.dtoToEntity(accountDto);
		}
		
		if(!user.addAccount(account))
			throw new UserHasAccountException("Bad request", "User already has the account.");
		
		this.userRepository.save(user);
		
		LOG.info("Account: {}, added in User: {}", user, accountDto);
		
		return UserMapper.entityToDto(user);
	}

	@Override
	public void delete(Long id) {
		this.userRepository.delete(this.findById(id));
		
		LOG.info("User with id: {}, successfuly deleted.", id);
	}

	@Override
	public void deleteAccount(Long userId, Long accountId) {
		User user = this.findById(userId);
		
		Account account = findAccountById(accountId);
		
		user.deleteAccount(account);
		
		this.userRepository.save(user);
		
		LOG.info("Account: {}, deleted from User: {}", account, user);
	}
	
	private User findById(Long userId) {
		return this.userRepository.findById(userId).orElseThrow(() ->
			new ItemNotFoundException("Item not found.", "User with id: " + userId + "not found."));
	}
	
	private Account findAccountById(Long accountId) {
		return this.accountRepository.findById(accountId).orElseThrow(() -> 
			new ItemNotFoundException("Item not found.", "Account with id: " + accountId + "not found."));
	}
}
