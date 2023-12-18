package com.arqui.integrador.service;

import static com.arqui.integrador.utils.ScooterMapper.*;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;

import com.arqui.integrador.dto.ScooterDTO;
import com.arqui.integrador.dto.ScooterListDTO;
import com.arqui.integrador.dto.ScooterNearestDTO;
import com.arqui.integrador.dto.ScooterOperationDTO;
import com.arqui.integrador.dto.ScooterPauseDTO;
import com.arqui.integrador.dto.ScooterReportDTO;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Scooter;
import com.arqui.integrador.repository.IScooterRepository;

@Service
public class ScooterService implements IScooterService{

	private static final Logger LOG = LoggerFactory.getLogger(ScooterService.class);
	
	@Value("${distance}")
	private int DISTANCE;
	
	private IScooterRepository scooterRepository;
	
	private RestTemplate restTemplate;
	
	public ScooterService(IScooterRepository scooterRepository, RestTemplate restTemplate) {
		this.scooterRepository = scooterRepository;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public List<ScooterDTO> getAll(String order) {
		List<ScooterDTO> response = new ArrayList<>();
		
		this.scooterRepository.findAll(Sort.by(Sort.Direction.ASC, order)).forEach(e -> 
			response.add(entityToDto(e)));
		
		LOG.info("Scooters: {} Quantity: {}", response, response.size());
		
		return response;
	}

	@Override
	public ScooterDTO getById(Long id) {
		Scooter response = this.findById(id);
		
		LOG.info("Scooter: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public ScooterDTO add(ScooterDTO scooterDto) {
		Scooter response = this.scooterRepository.save(dtoToEntity(scooterDto));
		
		LOG.info("Scooter added: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public ScooterDTO update(Long id, ScooterDTO scooterDto) {
		Scooter scooter = this.findById(id);
		
		scooterDto.setId(scooter.getId());
		
		this.scooterRepository.save(dtoToEntity(scooterDto));
		
		LOG.info("Scooter edited: {}", scooterDto);
		
		return scooterDto;
	}

	@Override
	public void delete(Long id) {
		
		this.scooterRepository.delete(this.findById(id));
		
		LOG.info("Scooter succesfully deleted.");
	}

	@Override
	public List<ScooterReportDTO> getScooterReport(Boolean pause_time) {
		
		List<ScooterPauseDTO> responseMsTravels = new ArrayList<>();
		
		List<ScooterReportDTO> list = this.scooterRepository.getScooterReport();
		
		if(pause_time) {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<List<Void>> requestEntity = new HttpEntity<>(headers);
			
			ResponseEntity<List<ScooterPauseDTO>> response = restTemplate.exchange(
					"lb://mcsv-travel:8080/travels/paused", 
					HttpMethod.GET, 
					requestEntity, 
					new ParameterizedTypeReference<List<ScooterPauseDTO>>() {}
			);
			if(response.getStatusCode().is2xxSuccessful()) {
				responseMsTravels.addAll(response.getBody());
			}
			
			for(ScooterReportDTO scooter: list) {
				for(ScooterPauseDTO scooterPause: responseMsTravels) {
					if(scooterPause.getId().equals(scooter.getId())) {
						scooter.setPauseTime(scooterPause.getPauseTime());
					}
				}
			}
		}
		
		return list;
	}

	@Override
	public List<ScooterOperationDTO> getScooterInOperation() {
		return this.scooterRepository.getScooterInOperation();
	}

	@Override
	public List<ScooterNearestDTO> getNearestScooters(double latitude, double longitude) {
		
		LOG.info("Getting nearest scooters - Latitude between {} and {}; Longitude between {} and {}", latitude-DISTANCE, latitude+DISTANCE, longitude-DISTANCE, longitude+DISTANCE);
		
		return this.scooterRepository.getNearestScooters(latitude-DISTANCE, latitude+DISTANCE, longitude-DISTANCE, longitude+DISTANCE);

	}
	
	private Scooter findById(Long id) {
		return this.scooterRepository.findById(id.intValue()).orElseThrow(() ->
			new ItemNotFoundException("Item not found.", "Item with id: " + id + "not found.")
		);
	}

	@Override
	public List<ScooterDTO> getAllAvailable(String order) {
		List<ScooterDTO> response = new ArrayList<>();
		
		this.scooterRepository.findAllAvailable(order, true).forEach(e -> 
			response.add(e));
		
		LOG.info("Scooters: {} Quantity: {}", response, response.size());
		
		return response;
	}

	@Transactional
	@Override
	public void updateScootersMaintenance(ScooterListDTO ids) {
		this.scooterRepository.updateScootersMaintenance(ids.getList());
		
		LOG.info("Scooters succesfully updated.");
		
	}

	@Override
	public List<ScooterDTO> getAllDisable(String order) {
		List<ScooterDTO> response = new ArrayList<>();
		
		this.scooterRepository.findAllAvailable(order, false).forEach(e -> 
			response.add(e));
		
		LOG.info("Scooters: {} Quantity: {}", response, response.size());
		
		return response;
	}
	
}
