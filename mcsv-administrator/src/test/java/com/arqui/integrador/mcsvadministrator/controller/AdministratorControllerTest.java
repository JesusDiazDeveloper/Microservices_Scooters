package com.arqui.integrador.mcsvadministrator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.exception.CustomExceptionHandler;
import com.arqui.integrador.mcsvadministrator.exception.ItemNotFoundException;
import com.arqui.integrador.mcsvadministrator.service.IAdministratorService;

@WebMvcTest(excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ContextConfiguration(classes = { AdministratorController.class, CustomExceptionHandler.class })
public class AdministratorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IAdministratorService adminService;
	
	@Test
	public void getAllTest() throws Exception {
		
		List<AdministratorDTO> admins = new ArrayList<>();
		admins.add(AdministratorDTO.builder().id(34037899L).name("meste73").password("password").rol("ADMIN").build());
		admins.add(AdministratorDTO.builder().id(34242399L).name("eljebu").password("password").rol("ADMIN").build());
		
		Mockito.when(adminService.getAll()).thenReturn(admins);
		
		mockMvc.perform(get("/administrator"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("meste73")))
			.andExpect(content().string(Matchers.containsString("eljebu")));
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		AdministratorDTO user = AdministratorDTO.builder().id(34037899L).name("meste73").password("password").rol("ADMIN").build();
		
		Mockito.when(adminService.getById(34037899L)).thenReturn(user);

		mockMvc.perform(get("/administrator/34037899"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("meste73")));
	}
	
	@Test
	public void getByIdNotFoundTest() throws Exception {
		
		Long id = 34037899L;
		
		Mockito.when(adminService.getById(id)).thenThrow(
				new ItemNotFoundException("Item not found.", "Item with id: " + id + " not found."));
		
		mockMvc.perform(get("/administrator/" + id))
			.andExpect(status().isNotFound())
			.andExpect(content().string(Matchers.containsString("Item not found.")));
	}
	
	@Test
	public void createTest() throws Exception{
		String requestBody = "{\"id\":34037899,\"name\":\"meste\",\"password\": \"password\",\"rol\":\"ADMIN\"}";

		AdministratorDTO adminEdited = AdministratorDTO.builder()
				.id(34037899L)
				.name("meste")
				.password("password")
				.rol("ADMIN")
				.build();
		
		Mockito.when(adminService.create(adminEdited)).thenReturn(adminEdited);
		
		mockMvc.perform(post("/administrator").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(content().string(Matchers.containsString("meste")))
			.andExpect(content().string(Matchers.containsString("password")));
	}
	
	@Test
	public void updateTest() throws Exception{
		String requestBody = "{\"id\":34037899,\"name\":\"meste\",\"password\": \"password\",\"rol\":\"ADMIN\"}";
		
		AdministratorDTO adminEdited = AdministratorDTO.builder()
				.id(34037899L)
				.name("meste")
				.password("password")
				.rol("ADMIN")
				.build();
		
		Mockito.when(adminService.update(34037899L, adminEdited)).thenReturn(adminEdited);
		
		mockMvc.perform((put("/administrator/34037899").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)))
		.andExpect(status().isOk())
		.andExpect(content().string(Matchers.containsString("meste")))
		.andExpect(content().string(Matchers.not(Matchers.containsString("meste73"))));
	}
	
	@Test
	public void deleteTest() throws Exception {
		mockMvc.perform(delete("/administrator/1"))
			.andExpect(status().isNoContent());
	}
}
