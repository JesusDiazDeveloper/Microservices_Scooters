package com.arqui.integrador.mcsvadministrator.service;

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

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.exception.ItemNotFoundException;
import com.arqui.integrador.mcsvadministrator.model.Administrator;
import com.arqui.integrador.mcsvadministrator.repository.IAdministratorRepository;
import com.arqui.integrador.mcsvadministrator.service.AdministratorService;

@ExtendWith(MockitoExtension.class)
class AdministratorServiceTest {
	
	@InjectMocks
	private AdministratorService administratorService;
	
	@Mock
	private IAdministratorRepository userRepository;
	
	@Mock
    private RestTemplate restTemplate;
	
	@Test
	void getAllTest(){
		List<Administrator> admins = new ArrayList<>();
		admins.add(Administrator.builder().id(34037899L).name("meste73").password("Ezequiel").rol("Mestelan").build());
		admins.add(Administrator.builder().id(34037899L).name("jebu").password("Jesus").rol("Diaz").build());
		admins.add(Administrator.builder().id(34037899L).name("frank").password("Franco").rol("Delucci").build());
		admins.add(Administrator.builder().id(34037899L).name("matt").password("Matias").rol("Sanchez Abrego").build());
		
		Mockito.when(this.userRepository.findAll()).thenReturn(admins);
		
		List<AdministratorDTO> usersDto = this.administratorService.getAll();
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
		
		Assertions.assertEquals(4L, usersDto.size());
		Assertions.assertEquals("meste73", admins.get(0).getName());
		Assertions.assertEquals("jebu", admins.get(1).getName());
		Assertions.assertEquals("frank", admins.get(2).getName());
		Assertions.assertEquals("matt", admins.get(3).getName());
		Assertions.assertEquals("Ezequiel", admins.get(0).getPassword());
		Assertions.assertEquals("Jesus", admins.get(1).getPassword());
		Assertions.assertEquals("Franco", admins.get(2).getPassword());
		Assertions.assertEquals("Matias", admins.get(3).getPassword());
		Assertions.assertEquals("Mestelan", admins.get(0).getRol());
		Assertions.assertEquals("Diaz", admins.get(1).getRol());
		Assertions.assertEquals("Delucci", admins.get(2).getRol());
		Assertions.assertEquals("Sanchez Abrego", admins.get(3).getRol());
	}
	
	@Test
	void getByIdTest() {
		Administrator admin = Administrator.builder().id(34037899L).name("meste73").password("Ezequiel").rol("Mestelan").build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(admin));
		
		AdministratorDTO adminDto = this.administratorService.getById(34037899L);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		
		Assertions.assertEquals("meste73", adminDto.getName());
		Assertions.assertEquals("Ezequiel", adminDto.getPassword());
		Assertions.assertEquals("Mestelan", adminDto.getRol());
	}
	
	@Test
	void getByIdNotFoundTest() {		
		Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());
		
		ItemNotFoundException expectedException = Assertions.assertThrows(
				ItemNotFoundException.class, () -> this.administratorService.getById(1L));
		
		Assertions.assertEquals("Item not found", expectedException.getMessage());
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
	}
	
	@Test
	void getByNullIdTest() {		
		Mockito.when(this.userRepository.findById(null)).thenThrow(IllegalArgumentException.class);
		
		IllegalArgumentException expectedException = Assertions.assertThrows(
				IllegalArgumentException.class, () -> this.administratorService.getById(null));
		
		Assertions.assertEquals("java.lang.IllegalArgumentException", expectedException.getClass().getName());
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(null);
	}

	@Test
	void createTest() {
		Administrator admin = Administrator.builder()
				.id(34037899L)
				.name("meste73")
				.password("Ezequiel")
				.rol("Mestelan")
				.build();
		
		AdministratorDTO adminDtoRequest = AdministratorDTO.builder()
				.id(34037899L)
				.name("meste73")
				.password("Ezequiel")
				.rol("Mestelan")
				.build();
		
		Mockito.when(this.userRepository.save(admin)).thenReturn(admin);
		
		AdministratorDTO userDtoResponse = this.administratorService.create(adminDtoRequest);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).save(admin);
		
		Assertions.assertEquals("meste73", userDtoResponse.getName());
		Assertions.assertEquals("Ezequiel", userDtoResponse.getPassword());
	}
	
	@Test
	void updateTest() {
		Administrator admin = Administrator.builder()
				.id(34037899L)
				.name("FranK")
				.password("Franco")
				.rol("Delucchi")
				.build();
		
		Administrator adminEdited = Administrator.builder()
				.id(34037899L)
				.name("meste73")
				.password("Ezequiel")
				.rol("Mestelan")
				.build();
		
		AdministratorDTO adminEditedDto = AdministratorDTO.builder()
				.id(34037899L)
				.name("meste73")
				.password("Ezequiel")
				.rol("Mestelan")
				.build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(admin));
		Mockito.when(this.userRepository.save(adminEdited)).thenReturn(adminEdited);
		
		AdministratorDTO userDto = this.administratorService.update(34037899L, adminEditedDto);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).save(adminEdited);
		
		Assertions.assertEquals("meste73", userDto.getName());
		Assertions.assertNotEquals("FranK", userDto.getName());
	}
	
	@Test
	void deleteTest() {
		Administrator admin = Administrator.builder()
				.id(34037899L)
				.name("FranK")
				.password("Franco")
				.rol("Delucchi")
				.build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(admin));
		
		this.administratorService.delete(34037899L);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
	}

}
