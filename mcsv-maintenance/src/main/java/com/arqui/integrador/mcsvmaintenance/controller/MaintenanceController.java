
package com.arqui.integrador.mcsvmaintenance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.service.IMaintenanceService;

import jakarta.validation.Valid;

@RestController
public class MaintenanceController implements IMaintenanceController {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceController.class);
    private IMaintenanceService maintenanceService;

    public MaintenanceController(IMaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @Override
    public ResponseEntity<List<MaintenanceDTO>> getAll() {
        List<MaintenanceDTO> response = this.maintenanceService.getAll();

        LOG.info("Getting all maintenances: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MaintenanceDTO> create(@Valid MaintenanceDTO maintenenceDto) {
        MaintenanceDTO response = this.maintenanceService.create(maintenenceDto);

        LOG.info("Creating maitenance : {}", response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MaintenanceDTO> getById(Long id) {
        MaintenanceDTO response = this.maintenanceService.getById(id);

        LOG.info("Getting maitenance by id: {}", response);

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<MaintenanceDTO> update(Long id, @Valid MaintenanceDTO maintenenceDto) {
        MaintenanceDTO response = this.maintenanceService.update(id, maintenenceDto);

        LOG.info("Updating maitenance: {} with id: {}", maintenenceDto, id);

        return ResponseEntity.ok(response);
    }

    @Override
    public void delete(Long id) {
        this.maintenanceService.delete(id);
        LOG.info("Deletin maintenance with id : {}", id);
    }

    @Override
    public List<Long> getScootersForMaintenance( Boolean available) {
        return this.maintenanceService.getScootersForMaintenance(available);
    }

    @Override
    public ResponseEntity<MaintenanceDTO> finalizeMaintenance(Long id) {
        MaintenanceDTO response = this.maintenanceService.finalizeMaintenance(id);

        LOG.info("Updating finalization maitenance with id: {}", id);

        return ResponseEntity.ok(response);
    }
}
