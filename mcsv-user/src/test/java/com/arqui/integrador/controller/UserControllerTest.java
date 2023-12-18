package com.arqui.integrador.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.ScooterDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.CustomExceptionHandler;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.service.IUserService;

@WebMvcTest(excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ContextConfiguration(classes = { UserController.class, CustomExceptionHandler.class })
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IUserService userService;
	
	@Test
	public void getAllTest() throws Exception {
		
		List<UserDto> users = new ArrayList<>();
		users.add(UserDto.builder().id(34037899L).name("meste73").firstname("Ezequiel").surname("Mestelan").build());
		users.add(UserDto.builder().id(34242399L).name("eljebu").firstname("Jesus").surname("Diaz").build());
		
		Mockito.when(userService.getAll()).thenReturn(users);
		
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("Ezequiel")))
			.andExpect(content().string(Matchers.containsString("Jesus")));
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		UserDto user = UserDto.builder().id(34037899L).name("meste73").firstname("Ezequiel").surname("Mestelan").build();
		
		Mockito.when(userService.getById(34037899L)).thenReturn(user);

		mockMvc.perform(get("/users/34037899"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("Ezequiel")));
	}
	
	@Test
	public void getByIdNotFoundTest() throws Exception {
		
		Long id = 34037899L;
		
		Mockito.when(userService.getById(id)).thenThrow(
				new ItemNotFoundException("Item not found.", "Item with id: " + id + " not found."));
		
		mockMvc.perform(get("/users/" + id))
			.andExpect(status().isNotFound())
			.andExpect(content().string(Matchers.containsString("Item not found")));
	}
	
	@Test
	public void getNearestScootersTest() throws Exception {
		
		double latitude = 50;
		double longitude = 50;
		
		List<ScooterDto> scooters = new ArrayList<>();
		
		scooters.add(ScooterDto.builder().id(1L).latitude(50L).longitude(40L).build());
		scooters.add(ScooterDto.builder().id(2L).latitude(45L).longitude(55L).build());
		
		Mockito.when(userService.getNearScooters(latitude, longitude)).thenReturn(scooters);
		
		mockMvc.perform(get("/users/nearest-scooters/lat/50/long/50"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void createTest() throws Exception{
		String requestBody = "{\"id\":34037899,\"name\":\"meste73\",\"cellphone\":2494380393,\"email\": \"elmeste.88@gmail.com\",\"firstname\":\"Ezequiel\",\"surname\":\"Mestelan\"}";

		UserDto user = UserDto.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan").build();
		
		Mockito.when(userService.create(user)).thenReturn(user);
		
		mockMvc.perform(post("/users").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(content().string(Matchers.containsString("meste73")))
			.andExpect(content().string(Matchers.containsString("Ezequiel")));
	}
	
	@Test
	public void updateTest() throws Exception{
		String requestBody = "{\"id\":34037899,\"name\":\"meste\",\"cellphone\":2494380393,\"email\": \"elmeste.88@gmail.com\",\"firstname\":\"Ezequiel\",\"surname\":\"Mestelan\"}";
		
		UserDto userEdited = UserDto.builder()
				.id(34037899L)
				.name("meste")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan").build();
		
		Mockito.when(userService.update(34037899L, userEdited)).thenReturn(userEdited);
		
		mockMvc.perform((put("/users/34037899").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)))
		.andExpect(status().isOk())
		.andExpect(content().string(Matchers.containsString("meste")))
		.andExpect(content().string(Matchers.not(Matchers.containsString("meste73"))));
	}
	
	@Test
	public void deleteTest() throws Exception {
		mockMvc.perform(delete("/users/1"))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void addAccountTest() throws Exception{
		String requestBody = "{\"id\":34037899,\"dateOfSign\":\"2023-10-10\",\"amount\":500.5,\"mpAccount\": 251629,\"isAvailable\": true}";
		
		AccountDto account = AccountDto.builder().mpAccount(123456).isAvailable(true).build();
		ArrayList<AccountDto> accounts = new ArrayList<>();
		accounts.add(account);
		
		UserDto userEdited = UserDto.builder()
				.id(34037899L)
				.name("meste")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan").build();
		
		userEdited.setAccounts(accounts);
		
		Mockito.when(this.userService.addAccount(34037899L, account)).thenReturn(userEdited);
		
		mockMvc.perform(put("/users/34037899/account").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		
		Assertions.assertEquals(userEdited.getAccounts().size(), 1);
	}
	
	@Test
	public void deleteAccountTest() throws Exception{
		mockMvc.perform(delete("/users/34037899/account/1"))
		.andExpect(status().isNoContent());
	}
}
