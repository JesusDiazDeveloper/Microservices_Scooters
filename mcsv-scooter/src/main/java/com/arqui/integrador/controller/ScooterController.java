package com.arqui.integrador.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.arqui.integrador.dto.ScooterDTO;
import com.arqui.integrador.dto.ScooterListDTO;
import com.arqui.integrador.dto.ScooterNearestDTO;
import com.arqui.integrador.dto.ScooterOperationDTO;
import com.arqui.integrador.dto.ScooterReportDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.service.IScooterService;

@RestController
public class ScooterController implements IScooterController {
	
	private IScooterService scooterService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ScooterController.class);
	
	public ScooterController(IScooterService scooterService) {
		this.scooterService = scooterService;
	}

	@Override
	public ResponseEntity<List<ScooterDTO>> getAll(String order, Boolean available) {
		if(available!=null) {
			if(available) {
				List<ScooterDTO> response = this.scooterService.getAllAvailable(order);
				
				LOG.info("Getting all enable scooters: {} order by: {}", response, order);
				
				return ResponseEntity.ok(response);
			}else {
				List<ScooterDTO> response = this.scooterService.getAllDisable(order);
				
				LOG.info("Getting all disable scooters: {} order by: {}", response, order);
				
				return ResponseEntity.ok(response);
			}
		}else {
			List<ScooterDTO> response = this.scooterService.getAll(order);
			
			LOG.info("Getting all scooters: {} order by: {}", response, order);
				
			return ResponseEntity.ok(response);
		}
		
	}

	@Override
	public ResponseEntity<ScooterDTO> getById(Long id) {
		
		ScooterDTO response = this.scooterService.getById(id);
		
		LOG.info("Getting scooters by id: {}", id);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ScooterDTO> add(ScooterDTO scooter) {
		
		ScooterDTO response = this.scooterService.add(scooter);
		
		LOG.info("Creating scooter with request body: {}", scooter);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ScooterDTO> update(Long id, ScooterDTO scooter) {
		
		LOG.info("Editing scooter by id: {} with request body: {}", id, scooter);
		
		return ResponseEntity.ok(this.scooterService.update(id, scooter));
	}

	@Override
	public void delete(Long id) {
	
		this.scooterService.delete(id);
		
		LOG.info("Deleting scooter by id: {}", id);
		
	}

	@Override
	public ResponseEntity<List<ScooterReportDTO>> getScooterReport(Boolean pause_time) {
		
		List<ScooterReportDTO> response = this.scooterService.getScooterReport(pause_time);
		
		LOG.info("Getting all scooters for report: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<List<ScooterOperationDTO>> getScooterInOperation() {
		List<ScooterOperationDTO> response = this.scooterService.getScooterInOperation();
		
		LOG.info("Getting quantity of scooters in operation and disabled: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<List<ScooterNearestDTO>> getNearestScooters(double latitude, double longitude){
		List<ScooterNearestDTO> response = this.scooterService.getNearestScooters(latitude, longitude);
		
		LOG.info("Getting nearest scooters: {}", response);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public void updateScootersMaintenance(ScooterListDTO ids) {
		this.scooterService.updateScootersMaintenance(ids);
		
		LOG.info("Updating scooters by id: {}", ids);
		
	}

}
