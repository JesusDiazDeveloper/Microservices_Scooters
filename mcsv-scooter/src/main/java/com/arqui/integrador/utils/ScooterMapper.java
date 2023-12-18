package com.arqui.integrador.utils;

import com.arqui.integrador.dto.ScooterDTO;
import com.arqui.integrador.model.Scooter;

public final class ScooterMapper {
	private ScooterMapper() {}
	
	public static ScooterDTO entityToDto(Scooter scooter) {
		return ScooterDTO.builder()
				.id(scooter.getId())
				.enabled(scooter.isEnabled())
				.kmsTraveled(scooter.getKmsTraveled())
				.usedTime(scooter.getUsedTime())
				.latitude(scooter.getLatitude())
				.longitude(scooter.getLongitude())
				.stationId(scooter.getStationId())
				.build();
	}
	
	public static Scooter dtoToEntity(ScooterDTO scooterDto) {
		return Scooter.builder()
				.id(scooterDto.getId())
				.enabled(scooterDto.isEnabled())
				.kmsTraveled(scooterDto.getKmsTraveled())
				.usedTime(scooterDto.getUsedTime())
				.latitude(scooterDto.getLatitude())
				.longitude(scooterDto.getLongitude())
				.stationId(scooterDto.getStationId())
				.build();
	}
	
	public static Scooter dtoToEntityNoId(ScooterDTO scooterDto) {
		return Scooter.builder()
				.enabled(scooterDto.isEnabled())
				.kmsTraveled(scooterDto.getKmsTraveled())
				.usedTime(scooterDto.getUsedTime())
				.latitude(scooterDto.getLatitude())
				.longitude(scooterDto.getLongitude())
				.stationId(scooterDto.getStationId())
				.build();
	}
	
	
}
