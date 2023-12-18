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
public class ScooterOperationDTO {
	private Long quantity;
	@NotNull
	private Boolean enabled;
}
