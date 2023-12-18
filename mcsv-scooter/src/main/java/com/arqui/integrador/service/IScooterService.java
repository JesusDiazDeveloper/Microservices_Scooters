package com.arqui.integrador.service;

import java.util.List;

import com.arqui.integrador.dto.ScooterDTO;
import com.arqui.integrador.dto.ScooterListDTO;
import com.arqui.integrador.dto.ScooterNearestDTO;
import com.arqui.integrador.dto.ScooterOperationDTO;
import com.arqui.integrador.dto.ScooterReportDTO;

public interface IScooterService {

	List<ScooterDTO> getAll(String order);

	ScooterDTO getById(Long id);

	ScooterDTO add(ScooterDTO scooter);

	ScooterDTO update(Long id, ScooterDTO scooter);

	void delete(Long id);

	List<ScooterReportDTO> getScooterReport(Boolean pause_time);

	List<ScooterOperationDTO> getScooterInOperation();
	
	List<ScooterNearestDTO> getNearestScooters(double latitude, double longitude);

	List<ScooterDTO> getAllAvailable(String order);
	
	List<ScooterDTO> getAllDisable(String order);
	
	void updateScootersMaintenance(ScooterListDTO ids);

}
