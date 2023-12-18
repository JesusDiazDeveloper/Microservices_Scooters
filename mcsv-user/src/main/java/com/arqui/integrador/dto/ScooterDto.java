package com.arqui.integrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {
	
	private Long id;
	
	private double latitude;
	
	private double longitude;
	
	private Long stationId;
}
