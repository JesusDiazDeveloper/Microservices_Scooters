package com.arqui.integrador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {
	private Long id;
	@NotBlank
	private String location;
	@NotNull
	private double latitude;
	@NotNull
	private double longitude;
}
