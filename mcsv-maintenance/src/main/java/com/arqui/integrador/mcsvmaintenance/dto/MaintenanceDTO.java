package com.arqui.integrador.mcsvmaintenance.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDTO {
    

    
    private long id_maintenance;

    @NotNull
	private LocalDate start_date;

	private LocalDate end_date;

    @NotNull
    private Long id_scooter;
    
    @NotNull
    private float scooter_km;
    
}

