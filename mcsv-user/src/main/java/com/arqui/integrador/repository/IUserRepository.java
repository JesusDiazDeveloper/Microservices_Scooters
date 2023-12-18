package com.arqui.integrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByEmail(String email);
}
