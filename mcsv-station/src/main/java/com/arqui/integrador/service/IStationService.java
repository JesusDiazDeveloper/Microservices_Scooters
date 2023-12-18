package com.arqui.integrador.service;

import java.util.List;

import com.arqui.integrador.dto.StationDTO;

public interface IStationService {
	List<StationDTO> getAll(String order);

	StationDTO getById(Long id);

	StationDTO add(StationDTO station);

	StationDTO update(Long id, StationDTO station);

	void delete(Long id);
}
