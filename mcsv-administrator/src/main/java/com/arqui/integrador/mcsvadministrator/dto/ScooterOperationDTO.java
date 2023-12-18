package com.arqui.integrador.mcsvadministrator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ScooterOperationDTO {
    private Boolean enabled;
    private Long quantity;
}
