package com.arqui.integrador.mcsvadministrator.service;

import java.util.List;

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.dto.FareDTO;
import com.arqui.integrador.mcsvadministrator.dto.ScooterOperationDTO;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByTotalBillingAmount;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByYearsDTO;

public interface IAdministratorService {

     List<AdministratorDTO> getAll();

     AdministratorDTO getById(Long id);

     AdministratorDTO create(AdministratorDTO administratorDto);

     AdministratorDTO update(Long id, AdministratorDTO administratorDto);

     void delete(Long id);

     void updateStatusAccount(Long id, String status);

    List<TravelsByYearsDTO> getTravelsByYears(int year, int quantity);

    TravelsByTotalBillingAmount getTravelsByTotalBillingAmounts(int year, int month1, int month2);

    List<Long> getAndSetScootersInMaintenance( Boolean available);

    List<ScooterOperationDTO> getScooterInOperation();

    void setNewFare(FareDTO f);

}
