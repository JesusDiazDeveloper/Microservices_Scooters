package com.arqui.integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.integrador.model.Station;

public interface IStationRepository extends JpaRepository<Station, Integer>{
	
}
