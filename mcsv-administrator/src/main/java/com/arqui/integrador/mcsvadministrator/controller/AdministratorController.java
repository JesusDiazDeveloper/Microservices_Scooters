package com.arqui.integrador.mcsvadministrator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.dto.FareDTO;
import com.arqui.integrador.mcsvadministrator.dto.ScooterOperationDTO;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByTotalBillingAmount;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByYearsDTO;
import com.arqui.integrador.mcsvadministrator.service.IAdministratorService;

import jakarta.validation.Valid;

@RestController
public class AdministratorController implements IAdministratorController {

    IAdministratorService administratorService;
    private static final Logger LOG = LoggerFactory.getLogger(AdministratorController.class);

    public AdministratorController(IAdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Override
    public ResponseEntity<List<AdministratorDTO>> getAll() {
        List<AdministratorDTO> response = this.administratorService.getAll();

        LOG.info("Getting all administrators: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AdministratorDTO> getById(Long id) {
        AdministratorDTO response = this.administratorService.getById(id);

        LOG.info("Getting administrator by id: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AdministratorDTO> create(@Valid AdministratorDTO administratorDTO) {
        AdministratorDTO response = this.administratorService.create(administratorDTO);

        LOG.info("Creating administrator: {}", response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<AdministratorDTO> update(Long id, @Valid AdministratorDTO administratorDTO) {
        AdministratorDTO response = this.administratorService.update(id, administratorDTO);
		
		LOG.info("Updating administrator: {} with id: {}", administratorDTO, id);
		
		return ResponseEntity.ok(response);    }

    @Override
    public void delete(Long id) {
        
		this.administratorService.delete(id);
		
		LOG.info("Deleting administrator with id: {}", id);
    }

    @Override
    public void unauthorizeAccount(Long id) {
        this.administratorService.updateStatusAccount(id , "unauthorize");
    }

    @Override
    public void authorizeAccount(Long id) {
       this.administratorService.updateStatusAccount( id , "authorize");
    }

    @Override
    public List<TravelsByYearsDTO> getTravelsByYears( int year , int quantity ) {
       return this.administratorService.getTravelsByYears( year ,  quantity );
    }

    @Override
    public TravelsByTotalBillingAmount getTravelsByTotalBillingAmounts(int year, int month1, int month2) {
        
        return this.administratorService.getTravelsByTotalBillingAmounts(year,month1,month2);
    }

    @Override
    public List<Long> getAndSetScootersInMaintenance( Boolean available) {
        return this.administratorService.getAndSetScootersInMaintenance( available );
    }

    @Override
    public List<ScooterOperationDTO> getScooterInOperation() {
        return this.administratorService.getScooterInOperation();
    }

    @Override
    public void setNewFare( FareDTO f) {
        this.administratorService.setNewFare(f);
    }

    


}
