package com.arqui.integrador.service;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.ScooterDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.exception.UserHasAccountException;
import com.arqui.integrador.model.Account;
import com.arqui.integrador.model.User;
import com.arqui.integrador.repository.IAccountRepository;
import com.arqui.integrador.repository.IUserRepository;
import com.arqui.integrador.util.AccountMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private IUserRepository userRepository;
	
	@Mock
	private IAccountRepository accountRepository;
	
	@Mock
    private RestTemplate restTemplate;
	
	@Test
	void getAllTest(){
		List<User> users = new ArrayList<>();
		users.add(User.builder().id(34037899L).name("meste73").firstname("Ezequiel").surname("Mestelan").build());
		users.add(User.builder().id(34037899L).name("jebu").firstname("Jesus").surname("Diaz").build());
		users.add(User.builder().id(34037899L).name("frank").firstname("Franco").surname("Delucci").build());
		users.add(User.builder().id(34037899L).name("matt").firstname("Matias").surname("Sanchez Abrego").build());
		
		Mockito.when(this.userRepository.findAll()).thenReturn(users);
		
		List<UserDto> usersDto = this.userService.getAll();
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
		
		Assertions.assertEquals(4L, usersDto.size());
		Assertions.assertEquals("meste73", users.get(0).getName());
		Assertions.assertEquals("jebu", users.get(1).getName());
		Assertions.assertEquals("frank", users.get(2).getName());
		Assertions.assertEquals("matt", users.get(3).getName());
		Assertions.assertEquals("Ezequiel", users.get(0).getFirstname());
		Assertions.assertEquals("Jesus", users.get(1).getFirstname());
		Assertions.assertEquals("Franco", users.get(2).getFirstname());
		Assertions.assertEquals("Matias", users.get(3).getFirstname());
		Assertions.assertEquals("Mestelan", users.get(0).getSurname());
		Assertions.assertEquals("Diaz", users.get(1).getSurname());
		Assertions.assertEquals("Delucci", users.get(2).getSurname());
		Assertions.assertEquals("Sanchez Abrego", users.get(3).getSurname());
	}
	
	@Test
	void getByIdTest() {
		User user = User.builder().id(34037899L).name("meste73").firstname("Ezequiel").surname("Mestelan").build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		
		UserDto userDto = this.userService.getById(34037899L);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		
		Assertions.assertEquals("meste73", userDto.getName());
		Assertions.assertEquals("Ezequiel", userDto.getFirstname());
		Assertions.assertEquals("Mestelan", userDto.getSurname());
	}
	
	@Test
	void getByIdNotFoundTest() {		
		Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());
		
		ItemNotFoundException expectedException = Assertions.assertThrows(
				ItemNotFoundException.class, () -> this.userService.getById(1L));
		
		Assertions.assertEquals("Item not found.", expectedException.getMessage());
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
	}
	
	@Test
	void getByNullIdTest() {		
		Mockito.when(this.userRepository.findById(null)).thenThrow(IllegalArgumentException.class);
		
		IllegalArgumentException expectedException = Assertions.assertThrows(
				IllegalArgumentException.class, () -> this.userService.getById(null));
		
		Assertions.assertEquals("java.lang.IllegalArgumentException", expectedException.getClass().getName());
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(null);
	}

	@Test
	void getNearScootersTest() {
		List<ScooterDto> scooters = new ArrayList<>();
		scooters.add(ScooterDto.builder().id(1L).latitude(50).longitude(50).stationId(111L).build());
		scooters.add(ScooterDto.builder().id(2L).latitude(60).longitude(40).stationId(222L).build());
		scooters.add(ScooterDto.builder().id(3L).latitude(70).longitude(30).stationId(333L).build());
		
		ResponseEntity<List<ScooterDto>> scootersResponseEntity = new ResponseEntity<>(scooters, HttpStatus.OK);
		
        Mockito.when(restTemplate.exchange(
                "http://127.0.0.1:8004/scooters/nearest/lat/50.0/long/50.0",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<ScooterDto>>() {}
        )).thenReturn(scootersResponseEntity);
        
        List<ScooterDto> scootersResponseDto = this.userService.getNearScooters(50, 50);
        
        Mockito.verify(restTemplate,Mockito.times(1)).exchange(
        		"http://127.0.0.1:8004/scooters/nearest/lat/50.0/long/50.0",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<ScooterDto>>() {});
        
        Assertions.assertEquals(111L, scootersResponseDto.get(0).getStationId());
        Assertions.assertEquals(222L, scootersResponseDto.get(1).getStationId());
        Assertions.assertEquals(333L, scootersResponseDto.get(2).getStationId());
	}
	
	@Test
	void getNearScootersEmptyTest() {
		ResponseEntity<List<ScooterDto>> scootersResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Mockito.when(restTemplate.exchange(
                "http://127.0.0.1:8004/scooters/nearest/lat/50.0/long/50.0",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<ScooterDto>>() {}
        )).thenReturn(scootersResponseEntity);
		
		Assertions.assertFalse(scootersResponseEntity.getStatusCode().is2xxSuccessful());
		
		List<ScooterDto> scootersResponseDto = this.userService.getNearScooters(50, 50);
		
		Mockito.verify(restTemplate, Mockito.times(1)).exchange(
                "http://127.0.0.1:8004/scooters/nearest/lat/50.0/long/50.0",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<ScooterDto>>() {});
		
		Assertions.assertEquals(0, scootersResponseDto.size());
	}
	
	@Test
	void createTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.build();
		
		UserDto userDtoRequest = UserDto.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.build();
		
		Mockito.when(this.userRepository.save(user)).thenReturn(user);
		
		UserDto userDtoResponse = this.userService.create(userDtoRequest);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).save(user);
		
		Assertions.assertEquals("meste73", userDtoResponse.getName());
		Assertions.assertEquals("Ezequiel", userDtoResponse.getFirstname());
	}
	
	@Test
	void updateTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.build();
		
		User userEdited = User.builder()
				.id(34037899L)
				.name("meste")
				.cellphone(2494380393L)
				.email("dev.mestelan@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.build();
		
		UserDto userEditedDto = UserDto.builder()
				.id(34037899L)
				.name("meste")
				.cellphone(2494380393L)
				.email("dev.mestelan@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		Mockito.when(this.userRepository.save(userEdited)).thenReturn(userEdited);
		
		UserDto userDto = this.userService.update(34037899L, userEditedDto);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).save(userEdited);
		
		Assertions.assertEquals("meste", userDto.getName());
		Assertions.assertNotEquals("meste73", userDto.getName());
	}
	
	@Test
	void deleteTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		
		this.userService.delete(34037899L);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
	}

	@Test
	void addAccountTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
		
		AccountDto accountDto = AccountDto.builder()
				.id(111L)
				.mpAccount(251629)
				.amount(200.0)
				.isAvailable(true)
				.build();
		
		User userResponse = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
	
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		Mockito.when(this.accountRepository.findByMpAccount(251629)).thenReturn(Optional.empty());
		
		userResponse.addAccount(AccountMapper.dtoToEntity(accountDto));
		
		Mockito.when(this.userRepository.save(user)).thenReturn(userResponse);
		
		UserDto userDto = this.userService.addAccount(34037899L, accountDto);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		Mockito.verify(this.accountRepository, Mockito.times(1)).findByMpAccount(251629);
		
		Assertions.assertEquals(1, userDto.getAccounts().size());
	}
	
	@Test
	void addExistenseAccountInRepositoryTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
		
		AccountDto accountDto = AccountDto.builder()
				.id(111L)
				.mpAccount(251629)
				.amount(200.0)
				.isAvailable(true)
				.build();
		
		Account account = Account.builder()
				.id(111L)
				.mpAccount(251629)
				.amount(200.0)
				.isAvailable(true)
				.build();
		
		User userResponse = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
	
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		Mockito.when(this.accountRepository.findByMpAccount(251629)).thenReturn(Optional.of(account));
		
		Account accountResponse = this.accountRepository.findByMpAccount(251629).get();
		
		userResponse.addAccount(accountResponse);
		
		Mockito.when(this.userRepository.save(user)).thenReturn(userResponse);
		
		UserDto userDto = this.userService.addAccount(34037899L, accountDto);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		Mockito.verify(this.accountRepository, Mockito.times(2)).findByMpAccount(251629);
		
		Assertions.assertEquals(1, userDto.getAccounts().size());
	}
	
	@Test
	void addExistenseAccountInUserTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
		
		Account account = Account.builder()
				.id(111L)
				.mpAccount(251629)
				.amount(200.0)
				.isAvailable(true)
				.build();
		
		user.addAccount(account);
	
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		Mockito.when(this.accountRepository.findByMpAccount(251629)).thenReturn(Optional.of(account));
		
		Account accountResponse = this.accountRepository.findByMpAccount(251629).get();
		
		UserHasAccountException expectedException = Assertions.assertThrows(
				UserHasAccountException.class, () -> this.userService.addAccount(34037899L, AccountMapper.entityToDto(accountResponse)));
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		Mockito.verify(this.accountRepository, Mockito.times(2)).findByMpAccount(251629);
		
		Assertions.assertEquals("Bad request", expectedException.getMessage());
	}
	
	@Test
	void deleteAccountTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
		
		Account account = Account.builder()
				.id(111L)
				.mpAccount(251629)
				.amount(200.0)
				.isAvailable(true)
				.build();
		
		user.addAccount(account);
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		Mockito.when(this.accountRepository.findById(111L)).thenReturn(Optional.of(account));
		
		this.userService.deleteAccount(34037899L, 111L);
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		Mockito.verify(this.accountRepository, Mockito.times(1)).findById(111L);
	}
	
	@Test
	void deleteAccountNotFoundTest() {
		User user = User.builder()
				.id(34037899L)
				.name("meste73")
				.cellphone(2494380393L)
				.email("elmeste.88@gmail.com")
				.firstname("Ezequiel")
				.surname("Mestelan")
				.accounts(new ArrayList<>())
				.build();
		
		Mockito.when(this.userRepository.findById(34037899L)).thenReturn(Optional.of(user));
		Mockito.when(this.accountRepository.findById(111L)).thenReturn(Optional.empty());
		
		ItemNotFoundException expectedException = Assertions.assertThrows(
				ItemNotFoundException.class, () -> this.userService.deleteAccount(34037899L, 111L));
		
		Mockito.verify(this.userRepository, Mockito.times(1)).findById(34037899L);
		Mockito.verify(this.accountRepository, Mockito.times(1)).findById(111L);
		
		Assertions.assertEquals("Item not found.", expectedException.getMessage());
	}
}
