package com.arqui.integrador.initializer;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.arqui.integrador.model.Account;
import com.arqui.integrador.repository.IAccountRepository;

import jakarta.annotation.PostConstruct;


@Component
public class AccountInitializer {
	
    private IAccountRepository accountRepository;
    private UserInitializer userInit;
    
    public AccountInitializer(IAccountRepository accountRepository, UserInitializer userInit) {
    	this.accountRepository = accountRepository;
    	this.userInit = userInit;
    }
    
    @PostConstruct
    public void init(){
    	try {
    		this.accountRepository.save(Account.builder().amount(12500)
    				.dateOfSign(LocalDate.of(2023,7,10)).mpAccount(10012456).isAvailable(true).build());
    		
    		this.accountRepository.save(Account.builder().amount(14700)
    				.dateOfSign(LocalDate.of(2023,4,22)).mpAccount(10013578).isAvailable(true).build());
    		
    		this.accountRepository.save(Account.builder().amount(13200)
    				.dateOfSign(LocalDate.of(2023,3,14)).mpAccount(10014101).isAvailable(true).build());
    		
    		this.accountRepository.save(Account.builder().amount(16000)
    				.dateOfSign(LocalDate.of(2023,8,7)).mpAccount(10017594).isAvailable(true).build());
    		
    		this.accountRepository.save(Account.builder().amount(18000)
    				.dateOfSign(LocalDate.of(2023,2,1)).mpAccount(10013365).isAvailable(true).build());
    		
    		this.accountRepository.save(Account.builder().amount(9100)
    				.dateOfSign(LocalDate.of(2023,9,28)).mpAccount(10013142).isAvailable(true).build());
    		
    		this.userInit.init();
    		
    	} catch(RuntimeException e) {
    		//Try catch added because the application creates values post construct, so in the next time that you will run the app, 
    		// without try catch it throws an error for data integrity violation and then the app would not start.
    	}
    }
}
