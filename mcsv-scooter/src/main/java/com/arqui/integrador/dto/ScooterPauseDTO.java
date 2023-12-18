package com.arqui.integrador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScooterPauseDTO {
	@JsonProperty("id_scooter")
	private Long id;
	@NotNull
	@JsonProperty("pause_time")
	private Long pauseTime;
}
