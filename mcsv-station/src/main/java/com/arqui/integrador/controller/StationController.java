package com.arqui.integrador.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.dto.StationDTO;
import com.arqui.integrador.service.IStationService;

import jakarta.validation.Valid;

@RestController
public class StationController implements IStationController{
	
	private IStationService stationService;
	
	private static final Logger LOG = LoggerFactory.getLogger(StationController.class);
	
	public StationController(IStationService stationService) {
		this.stationService = stationService;
	}

	@Override
	public ResponseEntity<List<StationDTO>> getAll(String order) {
		List<StationDTO> response = this.stationService.getAll(order);
		
		LOG.info("Getting all stations: {} order by: {}", response, order);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<StationDTO> getById(Long id) {
		StationDTO response = this.stationService.getById(id);
		
		LOG.info("Getting station by id: {}", id);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<StationDTO> add(StationDTO station) {
		StationDTO response = this.stationService.add(station);
		
		LOG.info("Creating station with request body: {}", station);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<StationDTO> update(Long id, StationDTO station) {
		LOG.info("Editing station by id: {} with request body: {}", id, station);
		
		return ResponseEntity.ok(this.stationService.update(id, station));
	}

	@Override
	public void delete(Long id) {
		LOG.info("Deleting station by id: {}", id);
		
		this.stationService.delete(id);
	}

}
