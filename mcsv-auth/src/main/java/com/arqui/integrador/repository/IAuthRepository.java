package com.arqui.integrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.model.UserAuth;

@Repository
public interface IAuthRepository extends JpaRepository<UserAuth, Long>{
	
	Optional<UserAuth> findByUsername(String username);
}
