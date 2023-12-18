package com.arqui.integrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.integrador.model.Account;

public interface IAccountRepository extends JpaRepository<Account, Long>{
	
	Optional<Account> findByMpAccount(Integer mpAccount);
}
