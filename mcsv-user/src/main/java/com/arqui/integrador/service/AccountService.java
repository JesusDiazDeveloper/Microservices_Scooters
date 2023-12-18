package com.arqui.integrador.service;

import static com.arqui.integrador.util.AccountMapper.dtoToEntity;
import static com.arqui.integrador.util.AccountMapper.entityToDto;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.AccountHasUserException;
import com.arqui.integrador.exception.AccountStateException;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Account;
import com.arqui.integrador.model.User;
import com.arqui.integrador.repository.IAccountRepository;
import com.arqui.integrador.repository.IUserRepository;
import com.arqui.integrador.util.UserMapper;

@Service
public class AccountService implements IAccountService{
	
	private IAccountRepository accountRepository;
	private IUserRepository userRepository;
	private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
	
	public AccountService(IAccountRepository accountRepository, IUserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<AccountDto> getAll() {
		List<AccountDto> response = this.accountRepository.findAll().stream().map(e -> entityToDto(e)).toList();
		
		LOG.info("Accounts: {}", response);
		
		return response;
	}

	@Override
	public AccountDto getById(Long id) {
		Account response = this.findById(id);
		
		LOG.info("Account : {}", response);
		
		return entityToDto(response);
	}

	@Override
	public AccountDto create(AccountDto accountDto) {
		Account response = this.accountRepository.save(dtoToEntity(accountDto));
		
		LOG.info("Account created: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public AccountDto update(Long id, AccountDto accountDto) {
		Account account = this.findById(id);
		
		accountDto.setId(account.getId());
		
		this.accountRepository.save(dtoToEntity(accountDto));
		
		LOG.info("Account updated: {}", accountDto);
		
		return accountDto;
	}
	
	@Override
	public AccountDto addUser(Long id, UserDto userDto) {
		Account account = this.findById(id);
		
		Optional<User> userOpt = this.userRepository.findByEmail(userDto.getEmail());
		
		User user;
		
		if(userOpt.isPresent()) {
			user = userOpt.get();
		} else {
			user = UserMapper.dtoToEntity(userDto);
		}
		
		if(!account.addUser(user)) {
			throw new AccountHasUserException("Bad request", "Account already has the user.");
		}
		
		this.accountRepository.save(account);
		
		LOG.info("Adding user: {} into Account: {}", user, account);
		
		return entityToDto(account);
	}
	
	@Override
	public AccountDto authorize(Long id) {
		Account response = this.findById(id);
		
		if(response.isAvailable())
			throw new AccountStateException("Bad request", "Account is already authorized.");
		
		response.setAvailable(true);
		
		this.accountRepository.save(response);
		
		LOG.info("Account authorized: {}", response);
		
		return entityToDto(response);
	}
	
	@Override
	public AccountDto unauthorize(Long id) {
		Account response = this.findById(id);
		
		if(!response.isAvailable())
			throw new AccountStateException("Bad request", "Account is already unauthorized.");
		
		response.setAvailable(false);
		
		this.accountRepository.save(response);
		
		LOG.info("Account unauthorized: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public void delete(Long id) {
		Account account = this.findById(id);
		
		this.accountRepository.delete(account);
		
		LOG.info("Account deleted: {}", account);
		
	}
	
	@Override
	public void deleteUser(Long accountId, Long userId) {
		Account account = this.findById(accountId);
		
		User user = this.findUserById(userId);
		
		account.deleteUser(user);
		
		this.accountRepository.save(account);
		
		LOG.info("User: {}, deleted from Account: {}", user, account);
	}
	
	private Account findById(Long id) {
		return this.accountRepository.findById(id).orElseThrow(() -> 
			new ItemNotFoundException("Item not found", "Account with id: " + id + " not found."));
	}
	
	private User findUserById(Long id) {
		return this.userRepository.findById(id).orElseThrow(() ->
			new ItemNotFoundException("Item not found", "User with id: " + id + " not found."));
	}
}
