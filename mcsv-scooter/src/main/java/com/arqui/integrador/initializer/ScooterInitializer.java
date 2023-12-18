package com.arqui.integrador.initializer;

import org.springframework.stereotype.Component;

import com.arqui.integrador.model.Scooter;
import com.arqui.integrador.repository.IScooterRepository;

import jakarta.annotation.PostConstruct;

@Component
public class ScooterInitializer {

	private IScooterRepository scooterRepository;

	public ScooterInitializer(IScooterRepository scooterRepository) {
		this.scooterRepository = scooterRepository;
	}
	
	@PostConstruct
	public void init() {
		try {
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380393L).usedTime(150)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609764L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380394L).usedTime(100)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609764L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380395L).usedTime(432)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609765L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380396L).usedTime(0)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609765L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380397L).usedTime(0)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609763L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(false).kmsTraveled(2494380398L).usedTime(956)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609763L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380399L).usedTime(30)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609763L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(false).kmsTraveled(2494380400L).usedTime(3600)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609763L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(true).kmsTraveled(2494380401L).usedTime(5)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609764L).build());
			
			this.scooterRepository.save(Scooter.builder().enabled(false).kmsTraveled(2494380402L).usedTime(150)
					.latitude(-37.32167).longitude(-59.13316).stationId(34609763L).build());
			
		} catch(RuntimeException e) {
			
		}
	}
}
