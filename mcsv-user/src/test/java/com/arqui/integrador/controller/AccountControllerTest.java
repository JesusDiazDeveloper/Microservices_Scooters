package com.arqui.integrador.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.CustomExceptionHandler;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.service.IAccountService;

@WebMvcTest(excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ContextConfiguration(classes = { AccountController.class, CustomExceptionHandler.class })
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IAccountService accountService;
	
	@Test
	public void getAllTest() throws Exception {
		
		List<AccountDto> accounts = new ArrayList<>();
		accounts.add(AccountDto.builder()
				.id(100001L)
				.dateOfSign(LocalDate.of(2023, 11, 14))
				.amount(1150.50)
				.mpAccount(251629)
				.isAvailable(true)
				.build());
		
		accounts.add(AccountDto.builder()
				.id(100002L)
				.dateOfSign(LocalDate.of(2023, 11, 14))
				.amount(1550.50)
				.mpAccount(251837)
				.isAvailable(true)
				.build());
		
		Mockito.when(accountService.getAll()).thenReturn(accounts);
		
		mockMvc.perform(get("/accounts")).andExpect(status().isOk());
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		AccountDto account = AccountDto.builder()
				.id(100001L)
				.dateOfSign(LocalDate.of(2023, 11, 14))
				.amount(1150.50)
				.mpAccount(251629)
				.isAvailable(true)
				.build();
		
		Mockito.when(accountService.getById(100001L)).thenReturn(account);
		
		mockMvc.perform(get("/accounts/100001")).andExpect(status().isOk());
	}
	
	@Test
	public void getByIdNotFoundTest() throws Exception {
		
		Long id = 10000L;
		
		Mockito.when(accountService.getById(id)).thenThrow(
				new ItemNotFoundException("Item not found.", "Item with id: " + id + " not found."));
		
		mockMvc.perform(get("/accounts/" + id))
			.andExpect(status().isNotFound())
			.andExpect(content().string(Matchers.containsString("Item not found.")));
	}
	
	@Test
	public void createTest() throws Exception{
		String requestBody = "{\"id\":100001,\"dateOfSign\":\"2023-11-14\",\"amount\":1500.0,\"mpAccount\": 251629,\"isAvailable\":true}";
		
		AccountDto account = AccountDto.builder()
				.id(100001L)
				.dateOfSign(LocalDate.of(2023, 11, 14))
				.amount(1150.0)
				.mpAccount(251629)
				.isAvailable(true)
				.build();
		
		Mockito.when(accountService.create(account)).thenReturn(account);
		
		mockMvc.perform(post("/accounts").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void updateTest() throws Exception {
		String requestBody = "{\"id\":100001,\"dateOfSign\":\"2023-11-14\",\"amount\":1500.0,\"mpAccount\": 251629,\"isAvailable\":true}";
		
		AccountDto accountEdited = AccountDto.builder()
				.id(100001L)
				.dateOfSign(LocalDate.of(2023, 11, 14))
				.amount(1150.0)
				.mpAccount(251629)
				.isAvailable(true)
				.build();
		
		Mockito.when(accountService.update(100001L, accountEdited)).thenReturn(accountEdited);
		
		mockMvc.perform(put("/accounts/100001").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteTest() throws Exception {
		
		mockMvc.perform(delete("/accounts/account/10001")).andExpect(status().isNoContent());
	}
	
	@Test
	public void authorizeAccount() throws Exception {
		mockMvc.perform(put("/accounts/100001/authorize").content("").contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void unauthorizeAccount() throws Exception {
		mockMvc.perform(put("/accounts/100001/unauthorize").content("").contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void addUserTest() throws Exception {
		String requestBody = "{\"id\":34037899,\"name\":\"meste73\",\"cellphone\":2494380393,\"email\": \"elmeste.88@gmail.com\",\"firstname\":\"Ezequiel\",\"surname\":\"Mestelan\"}";
		
		UserDto user = UserDto.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan").build();
		
		AccountDto account = AccountDto.builder()
				.id(100001L)
				.dateOfSign(LocalDate.of(2023, 11, 14))
				.amount(1150.0)
				.mpAccount(251629)
				.isAvailable(true)
				.build();
		
		Long id = 100001L;
		
		Mockito.when(accountService.addUser(id, user)).thenReturn(account);
		mockMvc.perform(put("/accounts/100001/user").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}
	
	@Test
	public void deleteUserTest() throws Exception {
		mockMvc.perform(delete("/accounts/100001/user/123")).andExpect(status().isNoContent());
	}
}
