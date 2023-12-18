package com.arqui.integrador.mcsvmaintenance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.exception.CustomExceptionHandler;
import com.arqui.integrador.mcsvmaintenance.exception.ItemNotFoundException;
import com.arqui.integrador.mcsvmaintenance.service.IMaintenanceService;

@WebMvcTest(excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ContextConfiguration(classes = { MaintenanceController.class, CustomExceptionHandler.class })
public class MaintenanceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IMaintenanceService maintenanceService;
	
	@Test
	public void getAllTest() throws Exception {
		
		List<MaintenanceDTO> maintenanceUsers = new ArrayList<>();
		maintenanceUsers.add(MaintenanceDTO.builder()
				.id_maintenance(34037899L)
				.start_date(LocalDate.of(2023, 11, 17))
				.end_date(LocalDate.of(2023, 11, 17))
				.id_scooter(111L)
				.scooter_km(100.0F)
				.build());
		
		maintenanceUsers.add(MaintenanceDTO.builder()
				.id_maintenance(34111222L)
				.start_date(LocalDate.of(2023, 11, 17))
				.end_date(LocalDate.of(2023, 11, 17))
				.id_scooter(222L)
				.scooter_km(100.0F)
				.build());
		
		Mockito.when(maintenanceService.getAll()).thenReturn(maintenanceUsers);
		
		mockMvc.perform(get("/maintenance"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		MaintenanceDTO user = MaintenanceDTO.builder()
				.id_maintenance(34037899L)
				.start_date(LocalDate.of(2023, 11, 17))
				.end_date(LocalDate.of(2023, 11, 17))
				.id_scooter(111L)
				.scooter_km(100.0F)
				.build();
		
		Mockito.when(maintenanceService.getById(34037899L)).thenReturn(user);

		mockMvc.perform(get("/maintenance/34037899"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getByIdNotFoundTest() throws Exception {
		
		Long id = 34037899L;
		
		Mockito.when(maintenanceService.getById(id)).thenThrow(
				new ItemNotFoundException("Item not found.", "Item with id: " + id + " not found."));
		
		mockMvc.perform(get("/maintenance/" + id))
			.andExpect(status().isNotFound())
			.andExpect(content().string(Matchers.containsString("Item not found.")));
	}
	
	@Test
	public void createTest() throws Exception{
		String requestBody = "{\"id_maintenance\":34037899,\"start_date\":\"2023-11-17\",\"end_date\":\"2023-11-17\",\"id_scooter\": 111,\"scooter_km\":100.0}";

		MaintenanceDTO user = MaintenanceDTO.builder()
				.id_maintenance(34037899L)
				.start_date(LocalDate.of(2023, 11, 17))
				.end_date(LocalDate.of(2023, 11, 17))
				.id_scooter(111L)
				.scooter_km(100.0F)
				.build();
		
		Mockito.when(maintenanceService.create(user)).thenReturn(user);
		
		mockMvc.perform(post("/maintenance").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void updateTest() throws Exception{
		String requestBody = "{\"id_maintenance\":34037899,\"start_date\":\"2023-11-17\",\"end_date\":\"2023-11-17\",\"id_scooter\": 111,\"scooter_km\":100.0}";

		MaintenanceDTO userEdited = MaintenanceDTO.builder()
				.id_maintenance(34037899L)
				.start_date(LocalDate.of(2023, 11, 17))
				.end_date(LocalDate.of(2023, 11, 17))
				.id_scooter(111L)
				.scooter_km(100.0F)
				.build();
		
		Mockito.when(maintenanceService.update(34037899L, userEdited)).thenReturn(userEdited);
		
		mockMvc.perform((put("/maintenance/34037899").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteTest() throws Exception {
		mockMvc.perform(delete("/maintenance/1"))
			.andExpect(status().isNoContent());
	}

}
