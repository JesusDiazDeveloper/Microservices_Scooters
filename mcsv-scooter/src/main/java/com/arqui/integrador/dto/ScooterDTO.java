package com.arqui.integrador.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ScooterDTO {
	
	private Long id;
	@NotNull
	private boolean enabled;
	@NotNull
	private float kmsTraveled;
	@NotNull
	private float usedTime;
	@NotNull
	private double latitude;
	@NotNull
	private double longitude;
	@NotNull
	private Long stationId;
}
