package com.arqui.integrador.initializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.arqui.integrador.model.Travel;
import com.arqui.integrador.repository.TravelRepository;

import jakarta.annotation.PostConstruct;

@Component
public class TravelsInitializer {

	private TravelRepository travelRepository;

	public TravelsInitializer(TravelRepository travelRepository) {
		this.travelRepository = travelRepository;
	}

	@PostConstruct
	public void init() {
		try {
			this.travelRepository.save(Travel.builder().id("a").id_account(1).id_user(1).id_scooter(1000)
					.start_date(LocalDateTime.of(2023, 06, 15, 0, 0)).ending_date(null).pause_time(0)
					.km(BigDecimal.valueOf(45)).cost(Double.valueOf(5000)).paused(false).build());

			this.travelRepository.save(Travel.builder().id("b").id_account(2).id_user(2).id_scooter(1000)
					.start_date(LocalDateTime.of(2023, 06, 16, 0, 0)).ending_date(null).pause_time(0)
					.km(BigDecimal.valueOf(12)).cost(Double.valueOf(1500)).paused(false).build());

			this.travelRepository.save(Travel.builder().id("c").id_account(3).id_user(3).id_scooter(1000)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("d").id_account(1).id_user(1).id_scooter(1003)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("e").id_account(2).id_user(2).id_scooter(1003)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("f").id_account(3).id_user(3).id_scooter(1003)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("g").id_account(1).id_user(1).id_scooter(1006)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("h").id_account(2).id_user(2).id_scooter(1007)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("i").id_account(3).id_user(3).id_scooter(1008)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());
			
			this.travelRepository.save(Travel.builder().id("j").id_account(1).id_user(1).id_scooter(1009)
					.start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20)
					.km(BigDecimal.valueOf(55)).cost(Double.valueOf(6000)).paused(false).build());

		} catch (RuntimeException e) {

		}

	}
}
