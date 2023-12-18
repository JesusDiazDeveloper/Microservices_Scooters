package com.arqui.integrador.mcsvmaintenance.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.exception.ItemNotFoundException;
import com.arqui.integrador.mcsvmaintenance.model.Maintenance;
import com.arqui.integrador.mcsvmaintenance.repository.IMaintenanceRepository;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceTest {

    @InjectMocks
    private MaintenanceService maintenanceService;

    @Mock
    private IMaintenanceRepository maintenanceRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getAllTest() {
        List<Maintenance> maintenances = new ArrayList<>();
        maintenances.add(Maintenance.builder().id_maintenance(111).start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2)).id_scooter(123L).scooter_km(45.5f).build());
        maintenances.add(Maintenance.builder().id_maintenance(222).start_date(LocalDate.of(2023, 2, 2))
                .end_date(LocalDate.of(2023, 2, 2)).id_scooter(124L).scooter_km(5.5f).build());
        maintenances.add(Maintenance.builder().id_maintenance(333).start_date(LocalDate.of(2023, 3, 3))
                .end_date(LocalDate.of(2023, 3, 3)).id_scooter(125L).scooter_km(50.5f).build());

        Mockito.when(this.maintenanceRepository.findAll()).thenReturn(maintenances);

        List<MaintenanceDTO> maintenanceDTOs = this.maintenanceService.getAll();

        Mockito.verify(this.maintenanceRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(3L, maintenanceDTOs.size());
        Assertions.assertEquals(111, maintenanceDTOs.get(0).getId_maintenance());
        Assertions.assertEquals(222, maintenanceDTOs.get(1).getId_maintenance());
        Assertions.assertEquals(LocalDate.of(2023, 1, 1), maintenanceDTOs.get(0).getStart_date());
        Assertions.assertEquals(LocalDate.of(2023, 1, 2), maintenanceDTOs.get(0).getEnd_date());
        Assertions.assertEquals(LocalDate.of(2023, 3, 3), maintenanceDTOs.get(2).getStart_date());
        Assertions.assertEquals(LocalDate.of(2023, 3, 3), maintenanceDTOs.get(2).getEnd_date());
        Assertions.assertEquals(124L, maintenanceDTOs.get(1).getId_scooter());
        Assertions.assertEquals(125L, maintenanceDTOs.get(2).getId_scooter());
        Assertions.assertEquals(45.5f, maintenanceDTOs.get(0).getScooter_km());
        Assertions.assertEquals(5.5f, maintenanceDTOs.get(1).getScooter_km());
    }

    @Test
    void getByIdTest() {
        Maintenance maintenance = Maintenance.builder().id_maintenance(34037899L).id_maintenance(111)
                .start_date(LocalDate.of(2023, 1, 1)).end_date(LocalDate.of(2023, 1, 2)).id_scooter(123L)
                .scooter_km(45.5f).build();

        Mockito.when(this.maintenanceRepository.findById(34037899L)).thenReturn(Optional.of(maintenance));

        MaintenanceDTO maintenanceDTO = this.maintenanceService.getById(34037899L);

        Mockito.verify(this.maintenanceRepository, Mockito.times(1)).findById(34037899L);

        Assertions.assertEquals(111, maintenanceDTO.getId_maintenance());
        Assertions.assertEquals(LocalDate.of(2023, 1, 1), maintenanceDTO.getStart_date());
        Assertions.assertEquals(LocalDate.of(2023, 1, 2), maintenanceDTO.getEnd_date());
        Assertions.assertEquals(123L, maintenanceDTO.getId_scooter());
        Assertions.assertEquals(45.5f, maintenanceDTO.getScooter_km());
    }

    @Test
    void getByIdNotFoundTest() {
        Mockito.when(this.maintenanceRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException expectedException = Assertions.assertThrows(
                ItemNotFoundException.class, () -> this.maintenanceService.getById(1L));

        Assertions.assertEquals("Item not found", expectedException.getMessage());

        Mockito.verify(this.maintenanceRepository, Mockito.times(1)).findById(1L);
    }

    @Test
	void getByNullIdTest() {		
		Mockito.when(this.maintenanceRepository.findById(null)).thenThrow(IllegalArgumentException.class);
		
		IllegalArgumentException expectedException = Assertions.assertThrows(
				IllegalArgumentException.class, () -> this.maintenanceService.getById(null));
		
		Assertions.assertEquals("java.lang.IllegalArgumentException", expectedException.getClass().getName());
		
		Mockito.verify(this.maintenanceRepository, Mockito.times(1)).findById(null);
	}

    @Test
	void createTest() {
		Maintenance maintenance = Maintenance.builder()
				.id_maintenance(34037899L).id_maintenance(111)
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2))
                .id_scooter(123L)
                .scooter_km(45.5f)
                .build();
		
		MaintenanceDTO maintenanceDtoRequest = MaintenanceDTO.builder()
				.id_maintenance(34037899L)
                .id_maintenance(111)
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2))
                .id_scooter(123L)
                .scooter_km(45.5f)
                .build();
		
		Mockito.when(this.maintenanceRepository.save(maintenance)).thenReturn(maintenance);
		
		MaintenanceDTO maintenanceDtoResponse = this.maintenanceService.create(maintenanceDtoRequest);
		
		Mockito.verify(this.maintenanceRepository, Mockito.times(1)).save(maintenance);
		
		Assertions.assertEquals(123L, maintenanceDtoResponse.getId_scooter());
		Assertions.assertEquals(45.5f, maintenanceDtoResponse.getScooter_km());
	}

    @Test
	void updateTest() {
		Maintenance maintenance = Maintenance.builder()
                .id_maintenance(111L)
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2))
                .id_scooter(222222L)
                .scooter_km(10.5f)
                .build();
		
		Maintenance maintenanceEdited = Maintenance.builder()
                .id_maintenance(111L)
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2))
                .id_scooter(123L)
                .scooter_km(45.5f)
                .build();
		
		MaintenanceDTO maintenanceEditedDto = MaintenanceDTO.builder()
                .id_maintenance(111L)
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2))
                .id_scooter(123L)
                .scooter_km(45.5f)
                .build();
		
		Mockito.when(this.maintenanceRepository.findById(111L)).thenReturn(Optional.of(maintenance));
		Mockito.when(this.maintenanceRepository.save(maintenanceEdited)).thenReturn(maintenanceEdited);
		
		MaintenanceDTO maintenanceDto = this.maintenanceService.update(111L, maintenanceEditedDto);
		
		Mockito.verify(this.maintenanceRepository, Mockito.times(1)).save(maintenanceEdited);
		
		Assertions.assertEquals(45.5f, maintenanceDto.getScooter_km());
		Assertions.assertNotEquals(10.5f, maintenanceDto.getScooter_km());
		Assertions.assertEquals(123L, maintenanceDto.getId_scooter());
		Assertions.assertNotEquals(222222L, maintenanceDto.getScooter_km());
	}


    @Test
	void deleteTest() {
		Maintenance maintenance = Maintenance.builder()
				.id_maintenance(111L)
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2023, 1, 2))
                .id_scooter(222222L)
                .scooter_km(10.5f)
                .build();
		
		Mockito.when(this.maintenanceRepository.findById(111L)).thenReturn(Optional.of(maintenance));
		
		this.maintenanceService.delete(111L);
		
		Mockito.verify(this.maintenanceRepository, Mockito.times(1)).findById(111L);
        Mockito.verify(this.maintenanceRepository, Mockito.times(1)).delete(maintenance);

	}

}
