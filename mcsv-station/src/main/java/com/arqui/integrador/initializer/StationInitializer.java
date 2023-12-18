package com.arqui.integrador.initializer;

import org.springframework.stereotype.Component;

import com.arqui.integrador.model.Station;
import com.arqui.integrador.repository.IStationRepository;

import jakarta.annotation.PostConstruct;

@Component
public class StationInitializer {

	private IStationRepository stationRepository;

	public StationInitializer(IStationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}
	
	@PostConstruct
	public void init() {
		try {
			this.stationRepository
			.save(Station.builder().location("Tandil").latitude(-37.32167).longitude(-59.13316).build());
			
			this.stationRepository
			.save(Station.builder().location("Tandil").latitude(-37.19180).longitude(-59.07594).build());
			
			this.stationRepository
			.save(Station.builder().location("Tandil").latitude(-37.25645).longitude(-59.10076).build());
			
		} catch(RuntimeException e) {
			
		}

	}
}
