package com.arqui.integrador.mcsvmaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScooterForMaintenanceDTO {
        private Long id;
        private boolean enabled;
        private float kmsTraveled;
        private float usedTime;
        private double latitude;
        private double longitude;
        private Long stationId;
    }
