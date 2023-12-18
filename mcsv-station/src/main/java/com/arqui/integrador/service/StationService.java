package com.arqui.integrador.service;

import static com.arqui.integrador.utils.StationMapper.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arqui.integrador.dto.StationDTO;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Station;
import com.arqui.integrador.repository.IStationRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StationService implements IStationService{
	
	private static final Logger LOG = LoggerFactory.getLogger(StationService.class);
	
	private IStationRepository stationRepository;
	
	public StationService(IStationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	@Override
	public List<StationDTO> getAll(String order) {
		List<StationDTO> response = new ArrayList<>();
		
		this.stationRepository.findAll(Sort.by(Sort.Direction.ASC, order)).forEach(e -> 
			response.add(entityToDto(e)));
		
		LOG.info("Stations: {} Quantity: {}", response, response.size());
		
		return response;
	}

	@Override
	public StationDTO getById(Long id) {
		Station response = this.findById(id);
		
		LOG.info("Station: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public StationDTO add(StationDTO stationDto) {
		Station response = this.stationRepository.save(dtoToEntity(stationDto));
		
		LOG.info("Scooter added: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public StationDTO update(Long id, StationDTO stationDto) {
		Station station = this.findById(id);
		
		stationDto.setId(station.getId());
		
		this.stationRepository.save(dtoToEntity(stationDto));
		
		LOG.info("Station edited: {}", stationDto);
		
		return stationDto;
	}

	@Override
	public void delete(Long id) {
		this.stationRepository.delete(this.findById(id));
		
		LOG.info("Station succesfully deleted.");
	}
	
	private Station findById(Long id) {
		
		return this.stationRepository.findById(id.intValue()).orElseThrow(() ->
		new ItemNotFoundException("Item not found.", "Item with id: " + id + "not found.")
	);
	}
}
