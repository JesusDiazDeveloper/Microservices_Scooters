package com.arqui.integrador.mcsvmaintenance.initializer;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.arqui.integrador.mcsvmaintenance.model.Maintenance;
import com.arqui.integrador.mcsvmaintenance.repository.IMaintenanceRepository;

import jakarta.annotation.PostConstruct;

@Component
public class MaintenanceInitializer {

	private IMaintenanceRepository maintenanceRepository;

	public MaintenanceInitializer(IMaintenanceRepository maintenanceRepository) {
		this.maintenanceRepository = maintenanceRepository;
	}
	
	@PostConstruct
	public void init() {
		try {
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2023, 5, 14))
					.end_date(LocalDate.of(2023, 5, 16)).id_scooter(210789L).scooter_km(1500).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2023, 1, 02))
					.end_date(LocalDate.of(2023, 5, 05)).id_scooter(210789L).scooter_km(500).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2022, 11, 23)).end_date(null)
					.id_scooter(210789L).scooter_km(4000).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2020, 6, 01))
					.end_date(LocalDate.of(2020, 6, 02)).id_scooter(210789L).scooter_km(1750).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2021, 8, 12))
					.end_date(LocalDate.of(2023, 5, 14)).id_scooter(210789L).scooter_km(9000).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2023, 5, 30))
					.end_date(LocalDate.of(2023, 6, 03)).id_scooter(210789L).scooter_km(2345).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2022, 3, 21))
					.end_date(LocalDate.of(2022, 3, 24)).id_scooter(210789L).scooter_km(1000).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2022, 4, 02))
					.end_date(LocalDate.of(2022, 4, 10)).id_scooter(210789L).scooter_km(2015).build());
			
			this.maintenanceRepository.save(Maintenance.builder().start_date(LocalDate.of(2022, 12, 20))
					.end_date(LocalDate.of(2023, 1, 14)).id_scooter(210789L).scooter_km(0).build());
		} catch(RuntimeException e) {
			
		}

	}
}
