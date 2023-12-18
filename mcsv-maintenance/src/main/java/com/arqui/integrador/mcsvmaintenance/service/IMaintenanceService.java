package com.arqui.integrador.mcsvmaintenance.service;

import java.util.List;


import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;

public interface IMaintenanceService {
    
    List<MaintenanceDTO> getAll();
	
	MaintenanceDTO getById(Long id);
	
	MaintenanceDTO create(MaintenanceDTO maintenanceDTO);
	
	MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO);
		
	void delete(Long id);

    List<Long> getScootersForMaintenance( Boolean available);

   MaintenanceDTO finalizeMaintenance(Long id);
	
}
