package com.arqui.integrador.mcsvmaintenance.util;

import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.model.Maintenance;

public class MaintenanceMapper {
    
    public static MaintenanceDTO entityToDto(Maintenance maintenance) {
        return MaintenanceDTO.builder()
                .id_maintenance(maintenance.getId_maintenance())
                .start_date(maintenance.getStart_date())
                .end_date(maintenance.getEnd_date())
                .id_scooter(maintenance.getId_scooter())
                .scooter_km(maintenance.getScooter_km())
                // .scooter_usage_time(maintenance.getScooter_usage_time())
                .build();
    }
    
    public static Maintenance dtoToEntity(MaintenanceDTO maintenanceDto) {
        return Maintenance.builder()
                .id_maintenance(maintenanceDto.getId_maintenance())
                .start_date(maintenanceDto.getStart_date())
                .end_date(maintenanceDto.getEnd_date())
                .id_scooter(maintenanceDto.getId_scooter())
                .scooter_km(maintenanceDto.getScooter_km())
                // .scooter_usage_time(maintenanceDto.getScooter_usage_time())
                .build();
    }
}
	

