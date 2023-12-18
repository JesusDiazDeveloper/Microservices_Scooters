package com.arqui.integrador.mcsvmaintenance.service;

import static com.arqui.integrador.mcsvmaintenance.util.MaintenanceMapper.dtoToEntity;
import static com.arqui.integrador.mcsvmaintenance.util.MaintenanceMapper.entityToDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.mcsvmaintenance.dto.ListOfIdsToUpdateDTO;
import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.dto.ScooterForMaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.exception.ItemNotFoundException;
import com.arqui.integrador.mcsvmaintenance.model.Maintenance;
import com.arqui.integrador.mcsvmaintenance.repository.IMaintenanceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaintenanceService implements IMaintenanceService {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceService.class);
    private IMaintenanceRepository maintenanceRepository;
    private RestTemplate restTemplate;

    public MaintenanceService(IMaintenanceRepository maintenanceRepository, RestTemplate restTemplate) {
        this.maintenanceRepository = maintenanceRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<MaintenanceDTO> getAll() {
        List<MaintenanceDTO> response = this.maintenanceRepository.findAll().stream().map(e -> entityToDto(e)).toList();
        return response;
    }

    @Override
    public MaintenanceDTO getById(Long id) {
        Maintenance response = this.findById(id);

        LOG.info("Maintenance : {}", response);

        return entityToDto(response);
    }

    @Override
    public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
        Maintenance response = this.maintenanceRepository.save(dtoToEntity(maintenanceDTO));

        LOG.info("Maintenance created: {}", response);

        return entityToDto(response);
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
        Maintenance maintenance = this.findById(id);

        maintenanceDTO.setId_maintenance(maintenance.getId_maintenance());

        this.maintenanceRepository.save(dtoToEntity(maintenanceDTO));

        LOG.info("Maintenance updated: {}", maintenanceDTO);

        return maintenanceDTO;
    }

    @Override
    public void delete(Long id) {
        Maintenance maintenance = this.findById(id);

        this.maintenanceRepository.delete(maintenance);

        LOG.info("Maintenance deleted: {}", maintenance);
    }

    private Maintenance findById(Long id) {
        return this.maintenanceRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found", "Maintenance with id: " + id + " not found."));
    }

    @Override
    public List<Long> getScootersForMaintenance( Boolean available) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<Void>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ScooterForMaintenanceDTO>> response = restTemplate.exchange(
                "lb://mcsv-scooter:8080/scooters?available="+available,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ScooterForMaintenanceDTO>>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            return getIdsForMaintenance(response.getBody());
        } else {
            
            return new ArrayList<>();
        }
    }

    private List<Long> getIdsForMaintenance(List<ScooterForMaintenanceDTO> listForFilter) {
        List<Long> list = new ArrayList<>();
        listForFilter.forEach(e -> {
            if (e.getKmsTraveled() >= 1000) {
                Maintenance m = Maintenance.builder().start_date(LocalDate.now()).id_scooter(e.getId())
                        .scooter_km(e.getKmsTraveled()).build();
                this.maintenanceRepository.save(m);
                list.add(e.getId());
            } 
            
        });
        
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ListOfIdsToUpdateDTO> requestEntity = new HttpEntity<>( ListOfIdsToUpdateDTO.builder().list(list).build() , headers );

        restTemplate.exchange(
                "lb://mcsv-scooter:8080/scooters",
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<ListOfIdsToUpdateDTO>() {});
        return list;
    }

    @Override
    public MaintenanceDTO finalizeMaintenance(Long id) {
        Maintenance m = this.findById(id);
        m.setEnd_date(LocalDate.now());
        MaintenanceDTO maintenanceDTO = MaintenanceDTO.builder()
        .id_maintenance(m.getId_maintenance())
        .start_date(m.getStart_date())
        .end_date(m.getEnd_date())
        .id_scooter(m.getId_scooter())
        .scooter_km(m.getScooter_km())
        .build();

        this.maintenanceRepository.save(dtoToEntity(maintenanceDTO));

        LOG.info("Maintenance finalization updated: {}", maintenanceDTO);

        return maintenanceDTO;
    }


}
