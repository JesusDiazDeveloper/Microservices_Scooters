package com.arqui.integrador.utils;

import com.arqui.integrador.dto.StationDTO;
import com.arqui.integrador.model.Station;

public class StationMapper {
private StationMapper() {}
	
	public static StationDTO entityToDto(Station station) {
		return StationDTO.builder()
				.id(station.getId())
				.location(station.getLocation())
				.latitude(station.getLatitude())
				.longitude(station.getLongitude())
				.build();
	}
	
	public static Station dtoToEntity(StationDTO stationDto) {
		return Station.builder()
				.id(stationDto.getId())
				.location(stationDto.getLocation())
				.latitude(stationDto.getLatitude())
				.longitude(stationDto.getLongitude())
				.build();
	}
	
	public static Station dtoToEntityNoId(StationDTO stationDto) {
		return Station.builder()
				.location(stationDto.getLocation())
				.latitude(stationDto.getLatitude())
				.longitude(stationDto.getLongitude())
				.build();
	}
}
