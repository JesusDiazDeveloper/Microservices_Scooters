package com.arqui.integrador.mcsvadministrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.integrador.mcsvadministrator.model.Administrator;

public interface IAdministratorRepository extends JpaRepository<Administrator, Long>{
    
}
